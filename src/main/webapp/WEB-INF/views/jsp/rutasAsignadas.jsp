<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
<title>Rutas - Ubicaciones</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=0">
<spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
<spring:url value="/resources/core/css/common.css" var="commonCss" />
<spring:url value="/resources/core/DataTables/datatables.min.css" var="datatablesCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${commonCss}" rel="stylesheet" />
<link href="${datatablesCss}" rel="stylesheet" />
</head>

<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="${pageContext.request.contextPath}/">Rutas</a>
    </div>
  </div>
</nav>
<br>
<br>
<br>
<div class="container">
  <h2>
    <a href="${pageContext.request.contextPath}/" class="btn btn-info"> <span class="glyphicon glyphicon-triangle-left"></span>
    </a> Asignaci&oacute;n de rutas
  </h2>
</div>
<br>
<div class="container">
  <div class="table-responsive">
    <table class="table table-striped table-hover">
      <thead>
        <tr>
          <th>Vehiculo</th>
          <th>Ruta</th>
          <th>Partida</th>
          <th>Llegada</th>
        </tr>
      </thead>
      <tbody>
        <c:if test="${not empty rutasAsignadas}">
          <c:forEach items="${rutasAsignadas}" var="rutaAsignada">
            <tr class="link" id="${rutaAsignada.getId()}" title="${rutaAsignada.getId()}">
              <td>${rutaAsignada.getVehiculo().getNombreCorto()}</td>
              <td>${rutaAsignada.getRuta().getDescripcion()}</td>
              <td>${rutaAsignada.getFechahoraDePartida()}</td>
              <td>${rutaAsignada.getFechahoraDeLlegada()}</td>
            </tr>
          </c:forEach>
        </c:if>
      </tbody>
    </table>
  </div>
  <div class="row">
    <div class="col-xs-12 col-sm-2">
      <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#popUpAgregar">
        Agregar <span class="glyphicon glyphicon-plus-sign"></span>
      </button>
    </div>
  </div>
</div>

<div id="popUpAgregar" class="modal fade" role="dialog">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Agregar asignaci&oacute;n</h4>
      </div>
      <div class="modal-body">
        <c:if test="${not empty vehiculos}">
          <div class="form-group">
            <label>Veh&iacute;culo</label> <select name="placa" id="placa" class="form-control" autofocus>
              <c:forEach items="${vehiculos}" var="vehiculo">
                <option value="${vehiculo.getPlaca()}">${vehiculo.getNombreCorto()}</option>
              </c:forEach>
            </select>
          </div>
          <div class="form-group">
            <label>Ruta</label> <select name="ruta" id="ruta" class="form-control">
              <c:forEach items="${rutas}" var="ruta">
                <option value="${ruta.getId()}">${ruta.getDescripcion()}</option>
              </c:forEach>
            </select>
          </div>
          <div class="form-group date" id="fecha">
            <label>Fecha</label> <input id="fechaDePartida" class="form-control" type="date">
          </div>
          <div class="form-group date" id="horaPartida">
            <label>Hora de partida</label><input id="horaDePartida" class="form-control" type="time">
          </div>
          <div class="form-group date" id="horaLlegada">
            <label>Hora de llegada</label> <input id="horaDeLlegada" class="form-control" type="time">
          </div>
        </c:if>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal" id="agregar">Agregar</button>
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
<spring:url value="/resources/core/DataTables/datatables.min.js" var="datatablesJs" />

<script src="${jquery}"></script>
<script src="${coreJs}"></script>
<script src="${bootstrapJs}"></script>
<script src="${datatablesJs}"></script>
<script type="text/javascript">
resetearFechasHoras();

$("table").DataTable({
	"order": [[ 2, "desc" ]]
});

$("#agregar").click(function() {
  var fechahoraDePartida = getFechahora($("#horaDePartida").val());
  var fechahoraDeLlegada = getFechahora($("#horaDeLlegada").val());
  var timezoneOffset = new Date().getTimezoneOffset() * 60 * 1000;
  var partida = fechahoraDePartida.getTime() + timezoneOffset;
  var llegada = fechahoraDeLlegada.getTime() + timezoneOffset;
  
  $.ajax({
    url: "${pageContext.request.contextPath}/asignacion/agregar",
    method: "POST",
    data: {
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

$('.modal').on('hidden.bs.modal', function () {
  resetearFechasHoras();
  $(".modal select option:first").prop("selected", true);
})

$('body').delegate(".link", "click", function() {
  var id = $(this).prop("id");
  window.location.href = "/Rutas/asignacion/" + id;
});

function getFechahora(hora) {
  var fecha = $("#fechaDePartida").val();
  return new Date(fecha + "T" + hora);
}

function resetearFechasHoras() {
  var hoy = new Date();
  var fecha = hoy.getFullYear() + "-" + pad(hoy.getMonth() + 1) + "-" + pad(hoy.getDate());
  var hora1 = pad(hoy.getHours()) + ":" + pad(hoy.getMinutes());
  var hora2 = pad(hoy.getHours() + 1) + ":" + pad(hoy.getMinutes());
  $("#fechaDePartida").val(fecha);
  $("#horaDePartida").val(hora1);
  $("#horaDeLlegada").val(hora2);
}

function pad(numero) {
  var dosDigitos = numero > 9;
  if(dosDigitos) {
    return numero;
  }
  return "0" + numero;
}
</script>
</body>
</html>