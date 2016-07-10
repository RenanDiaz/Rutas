<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
<title>Rutas - Vehiculo</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=0">
<spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
<spring:url value="/resources/core/css/common.css" var="commonCss" />
<spring:url value="/resources/core/DataTables/datatables.min.js" var="datatablesJs" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${commonCss}" rel="stylesheet" />
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
    <a href="/Rutas/rutas" class="btn btn-info">
      <span class="glyphicon glyphicon-triangle-left"></span>
    </a>
    <c:if test="${not empty ruta}">
      ${ruta.getId()} - ${ruta.getDescripcion()}
    </c:if>
    <c:if test="${empty ruta}">
      No encontrado
    </c:if>
  </h2>
</div>
<br>
<c:if test="${not empty asignaciones}">
  <div class="container">
    <div class="table-responsive">
      <table class="table table-striped table-hover">
        <thead>
          <tr>
            <th>ID</th>
            <th>Vehiculo</th>
            <th>Partida</th>
            <th>Llegada</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${asignaciones}" var="asignacion">
            <tr class="link" id="${asignacion.getId()}">
              <td>${asignacion.getId()}</td>
              <td>${asignacion.getVehiculo().getNombreCorto()}</td>
              <td>${asignacion.getFechahoraDePartida()}</td>
              <td>${asignacion.getFechahoraDeLlegada()}</td>
            </tr>
          </c:forEach>
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
        <h4 class="modal-title">Editar ruta</h4>
      </div>
      <div class="modal-body">
        <c:if test="${not empty ruta}">
          <div class="form-group">
            <label>ID</label> <input id="id" class="form-control" type="text" disabled value="${ruta.getId()}">
          </div>
          <div class="form-group">
            <label>Origen</label> <input id="origen" class="form-control" type="text" required value="${ruta.getOrigen()}">
          </div>
          <div class="form-group">
            <label>Destino</label> <input id="destino" class="form-control" type="text" required value="${ruta.getDestino()}">
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
<spring:url value="/resources/core/DataTables/datatables.min.js" var="datatablesJs" />

<script src="${jquery}"></script>
<script src="${coreJs}"></script>
<script src="${bootstrapJs}"></script>
<script src="${datatablesJs}"></script>
<script type="text/javascript">
$("table").DataTable({
	"order": [[ 2, "desc" ]]
});

$("#guardar").click(function() {
  $.ajax({
    url: "${pageContext.request.contextPath}/rutas/editar",
    method: "POST",
    data: {
      id: $("#id").val(),
      origen: $("#origen").val(),
      destino: $("#destino").val()
    },
    success: function() {
      location.reload();
    }
  });
});

$(".link").click(function() {
  var id = $(this).prop('id');
  window.location.href = "${pageContext.request.contextPath}/asignacion/" + id;
});
</script>
</body>
</html>