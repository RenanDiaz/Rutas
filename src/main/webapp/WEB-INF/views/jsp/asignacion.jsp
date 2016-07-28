<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
<title>Rutas - Asignaci&oacute;n</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=0">
<spring:url value="/resources/core/js/jquery-3.0.0.js" var="jquery" />
<spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
<spring:url value="/resources/core/js/bootstrap.min.js" var="bootstrapJs" />
<spring:url value="/resources/core/css/common.css" var="commonCss" />
<spring:url value="/resources/core/js/common.js" var="commonJs" />
<spring:url value="/resources/core/DataTables/datatables.min.css" var="datatablesCss" />
<spring:url value="/resources/core/DataTables/datatables.min.js" var="datatablesJs" />

<script src="${jquery}"></script>
<link href="${bootstrapCss}" rel="stylesheet" />
<script src="${bootstrapJs}"></script>
<link href="${commonCss}" rel="stylesheet" />
<script src="${commonJs}"></script>
<link href="${datatablesCss}" rel="stylesheet" />
<script src="${datatablesJs}"></script>
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
    <c:if test="${not empty asignacion}">
      ${asignacion.getId()} ${asignacion.getDescripcion()}
    </c:if>
    <c:if test="${empty asignacion}">
      No encontrada
    </c:if>
  </h2>
</div>
<br>
<c:if test="${not empty asignacion}">
  <div class="container">
    <div class="row">
      <div class="col-sm-1">
        <h2>
          <a href="${pageContext.request.contextPath}/asignacion/${asignacion.getId() - 1}" class="btn btn-info" ${asignacion.getId() > 1 ? "" : "disabled"}><span class="glyphicon glyphicon-menu-left"></span></a>
        </h2>
      </div>
      <div class="table-responsive col-sm-10">
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
              <td>${asignacion.getId()}</td>
              <td>${asignacion.getVehiculo().getNombreCorto()}</td>
              <td>${asignacion.getRuta().getDescripcion()}</td>
              <td>${asignacion.getFechaDePartida()} ${asignacion.getHoraDePartida()}</td>
              <td>${asignacion.getFechaDeLlegada()} ${asignacion.getHoraDeLlegada()}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="col-sm-1">
        <h2>
          <a href="${pageContext.request.contextPath}/asignacion/${asignacion.getId() + 1}" class="btn btn-info" ${asignacion.getId() < total ? "" : "disabled"}><span class="glyphicon glyphicon-menu-right"></span></a>
        </h2>
      </div>
    </div>
    <hr>
    <div class="row">
      <div class="table-responsive">
        <table id="movimientos" class="table table-striped">
          <thead>
            <tr>
              <th>#</th>
              <th>Distancia (m)</th>
              <th>Tiempo (s)</th>
              <th>Velocidad (km/h)</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${movimientos}" var="movimiento" varStatus="loop">
              <tr>
                <td>${loop.index + 1}</td>
                <td>${movimiento.getDistanciaLinealEnMetros()}</td>
                <td>${movimiento.getDiferenciaDeTiempoEnMilisegundos() / 1000}</td>
                <td>${movimiento.getVelocidad()}</td>
              </tr>
            </c:forEach>
          </tbody>
          <tfoot>
            <tr>
              <th></th>
              <th>${distanciaTotal}</th>
              <th>${tiempoTotal}</th>
              <th>${velocidadPromedio}</th>
            </tr>
          </tfoot>
        </table>
      </div>
    </div>
    <div class="row">
      <div class="col-xs-6 col-sm-2">
        <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#popUpDeEditar">
          Editar <span class="glyphicon glyphicon-edit"></span>
        </button>
      </div>
      <div class="col-xs-6 col-sm-2">
        <button type="button" class="btn btn-info btn-lg" id="calcular">
          Distancia <span class="glyphicon glyphicon-road"></span>
        </button>
      </div>
      <div class="col-xs-6 col-sm-2">
        <button type="button" class="btn btn-info btn-lg" id="verMapa">
          Ver mapa <span class="glyphicon glyphicon-map-marker"></span>
        </button>
      </div>
    </div>
    <br> <br>
    <div class="row" id="iframe-row" style="display: none;">
      <div class="col-xs-12">
        <iframe id="iframe-mapa" style="width: 100%; height: 300px;"></iframe>
      </div>
      <br>
      <div class="col-xs-12">
        <a href="#" target="_blank" id="link-mapa">Abrir en una ventana aparte</a>
      </div>
    </div>
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
                <c:if test="${vehiculo.getPlaca() == asignacion.getVehiculo().getPlaca()}">
                  <option value="${vehiculo.getPlaca()}" selected>${vehiculo.getNombreCorto()}</option>
                </c:if>
                <c:if test="${vehiculo.getPlaca() != asignacion.getVehiculo().getPlaca()}">
                  <option value="${vehiculo.getPlaca()}">${vehiculo.getNombreCorto()}</option>
                </c:if>
              </c:forEach>
            </select>
          </div>
          <div class="form-group">
            <label>Ruta</label> <select name="ruta" id="ruta" class="form-control">
              <c:forEach items="${rutas}" var="ruta">
                <c:if test="${ruta.getId() == asignacion.getRuta().getId()}">
                  <option value="${ruta.getId()}" selected>${ruta.getDescripcion()}</option>
                </c:if>
                <c:if test="${ruta.getId() != asignacion.getRuta().getId()}">
                  <option value="${ruta.getId()}">${ruta.getDescripcion()}</option>
                </c:if>
              </c:forEach>
            </select>
          </div>
          <div class="form-group date" id="fecha">
            <label>Fecha</label> <input id="fechaDePartida" class="form-control" type="date" value="${asignacion.getFechaDePartida()}">
          </div>
          <div class="form-group date" id="horaPartida">
            <label>Hora de partida</label><input id="horaDePartida" class="form-control" type="time" step="300" value="${asignacion.getHoraDePartida()}">
          </div>
          <div class="form-group date" id="horaLlegada">
            <label>Hora de llegada</label> <input id="horaDeLlegada" class="form-control" type="time" step="300" value="${asignacion.getHoraDeLlegada()}">
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

<script type="text/javascript">
$("#movimientos").DataTable();

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
      id: "${asignacion.getId()}",
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

$("#verMapa").click(function() {
  var ruta = "${pageContext.request.contextPath}/mapa/asignacion/${asignacion.getId()}";
  $("#iframe-mapa").attr("src", ruta);
  $("#link-mapa").attr("href", ruta);
  $("#iframe-row").show();
});

$("#calcular").click(function() {
  $.ajax({
    url: "${pageContext.request.contextPath}/asignacion/calcular",
    method: "POST",
    data: {
      asignacion: "${asignacion.getId()}"
    },
    success: function(data) {
      alert("Distancia recorrida: " + data + "m");
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