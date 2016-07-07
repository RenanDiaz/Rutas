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
    <table class="table table-striped">
      <thead>
        <tr>
          <th>ID</th>
          <th>Vehiculo</th>
          <th>Ruta</th>
          <th>Inicio</th>
          <th>Fin</th>
        </tr>
      </thead>
      <tbody>
        <c:if test="${not empty rutasAsignadas}">
          <c:forEach items="${rutasAsignadas}" var="rutaAsignada">
            <tr class="link" id="${rutaAsignada.getId()}" title="${rutaAsignada.getId()}">
              <td>${rutaAsignada.getId()}</td>
              <td>${rutaAsignada.getVehiculo().getNombreCorto()}</td>
              <td>${rutaAsignada.getRuta().getDescripcion()}</td>
              <td>${rutaAsignada.getHoraInicial()}</td>
              <td>${rutaAsignada.getHoraFinal()}</td>
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
        <h4 class="modal-title">Agregar ubicaci&oacute;n</h4>
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
          <div class="form-group date" id="fechaInicio">
            <label>Inicio</label> <input id="inicio" class="form-control" type="text" required> <span class="input-group-addon"> <span class="glyphicon glyphicon-calendar"></span>
            </span>
          </div>
          <div class="form-group date" id="fechaFin">
            <label>Fin</label> <input id="fin" class="form-control" type="text" required> <span class="input-group-addon"> <span class="glyphicon glyphicon-calendar"></span>
            </span>
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
$("table").DataTable({
	"order": [[ 0, "desc" ]]
});

$("#agregar").click(function() {
  var d = new Date();
  var fecha = d.getTime();
  $.ajax({
    url: "${pageContext.request.contextPath}/asignacion/agregar",
    method: "POST",
    data: {
      placa: $("#placa").val(),
      ruta: $("#ruta").val(),
      inicio: new Date($("#inicio").val()).getTime(),
      fin: new Date($("#fin").val()).getTime()
    },
    success: function() {
      location.reload();
    }
  });
});

$('.modal').on('hidden.bs.modal', function () {
  $(".modal input").val("");
  $(".modal input[type='checkbox']").prop("checked", false);
  $(".disableable").prop("disabled", true);
  $(".modal select option:first").prop("selected", true);
})

$('body').delegate(".link", "click", function() {
  var id = $(this).prop("id");
  window.location.href = "/Rutas/asignacion/" + id;
});
</script>
</body>
</html>