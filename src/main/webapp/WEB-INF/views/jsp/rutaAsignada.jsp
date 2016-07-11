<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
<title>Rutas - Asignaci&oacute;n</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=0">
<spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
<spring:url value="/resources/core/css/common.css" var="commonCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${commonCss}" rel="stylesheet" />
<style type="text/css">
.hidden {
  display: none;
}
</style>
</head>

<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="/Rutas/">Rutas</a>
    </div>
  </div>
</nav>
<br><br><br>
<div class="container">
  <h2>
    <a href="/Rutas/asignacion" class="btn btn-info">
      <span class="glyphicon glyphicon-triangle-left"></span>
    </a>
    <c:if test="${not empty rutaAsignada}">
      ${rutaAsignada.getId()} ${rutaAsignada.getDescripcion()}
    </c:if>
    <c:if test="${empty rutaAsignada}">
      No encontrada
    </c:if>
  </h2>
</div>
<br>
<c:if test="${not empty rutaAsignada}">
  <div class="container">
    <div class="table-responsive">
      <table class="table table-striped">
        <thead>
          <tr>
            <th>ID</th>
            <th>Vehiculo</th>
            <th>Ruta</th>
            <th>Partida</th>
            <th>Llegada</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>${rutaAsignada.getId()}</td>
            <td>${rutaAsignada.getVehiculo().getNombreCorto()}</td>
            <td>${rutaAsignada.getRuta().getDescripcion()}</td>
            <td>${rutaAsignada.getFechaDePartida()} ${rutaAsignada.getHoraDePartida()}</td>
            <td>${rutaAsignada.getFechaDeLlegada()} ${rutaAsignada.getHoraDeLlegada()}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#popUpDeEditar">Editar <span class="glyphicon glyphicon-edit"></span></button>
  </div>
</c:if>

<div id="popUpDeEditar" class="modal fade" role="dialog">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Editar asignaci&oacute;n</h4>
      </div>
      <div class="modal-body">
        <c:if test="${not empty vehiculos}">
          <div class="form-group">
            <label>Veh&iacute;culo</label> <select name="placa" id="placa" class="form-control" autofocus>
              <c:forEach items="${vehiculos}" var="vehiculo">
                <c:if test="${vehiculo.getPlaca() == rutaAsignada.getVehiculo().getPlaca()}">
                  <option value="${vehiculo.getPlaca()}" selected>${vehiculo.getNombreCorto()}</option>
                </c:if>
                <c:if test="${vehiculo.getPlaca() != rutaAsignada.getVehiculo().getPlaca()}">
                  <option value="${vehiculo.getPlaca()}">${vehiculo.getNombreCorto()}</option>
                </c:if>
              </c:forEach>
            </select>
          </div>
          <div class="form-group">
            <label>Ruta</label> <select name="ruta" id="ruta" class="form-control">
              <c:forEach items="${rutas}" var="ruta">
                <c:if test="${ruta.getId() == rutaAsignada.getRuta().getId()}">
                  <option value="${ruta.getId()}" selected>${ruta.getDescripcion()}</option>
                </c:if>
                <c:if test="${ruta.getId() != rutaAsignada.getRuta().getId()}">
                  <option value="${ruta.getId()}">${ruta.getDescripcion()}</option>
                </c:if>
              </c:forEach>
            </select>
          </div>
          <div class="form-group date" id="fecha">
            <label>Fecha</label> <input id="fechaDePartida" class="form-control" type="date" value="${rutaAsignada.getFechaDePartida()}">
          </div>
          <div class="form-group date" id="horaPartida">
            <label>Hora de partida</label><input id="horaDePartida" class="form-control" type="time" step="300" value="${rutaAsignada.getHoraDePartida()}">
          </div>
          <div class="form-group date" id="horaLlegada">
            <label>Hora de llegada</label> <input id="horaDeLlegada" class="form-control" type="time" step="300" value="${rutaAsignada.getHoraDeLlegada()}">
          </div>
        </c:if>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal" id="guardar">Guardar</button>
        <button type="button" class="btn" data-dismiss="modal">Cancelar</button>
      </div>
    </div>
  </div>
</div>

<div class="container">
  <hr>
  <footer>
    <p>&copy; Ren&aacute;n D&iacute;az Reyes 2016</p>
  </footer>
</div>

<spring:url value="/resources/core/js/jquery-3.0.0.js" var="jquery" />
<spring:url value="/resources/core/js/common.js" var="coreJs" />
<spring:url value="/resources/core/js/bootstrap.min.js" var="bootstrapJs" />

<script src="${jquery}"></script>
<script src="${coreJs}"></script>
<script src="${bootstrapJs}"></script>
<script type="text/javascript">
$("#guardar").click(function() {
  var fechahoraDePartida = getFechahora($("#horaDePartida").val());
  var fechahoraDeLlegada = getFechahora($("#horaDeLlegada").val());
  var timezoneOffset = new Date().getTimezoneOffset() * 60 * 1000;
  var partida = fechahoraDePartida.getTime() + timezoneOffset;
  var llegada = fechahoraDeLlegada.getTime() + timezoneOffset;
	
  $.ajax({
    url: "${pageContext.request.contextPath}/asignacion/editar",
    method: "POST",
    data: {
      id: "${rutaAsignada.getId()}",
      placa: $("#placa").val(),
      ruta: $("#ruta").val(),
      partida: partida,
      llegada: llegada
    },
    success: function() {
      location.reload();
    }
  });
});

function getFechahora(hora) {
  var fecha = $("#fechaDePartida").val();
  return new Date(fecha + "T" + hora);
}
</script>
</body>
</html>