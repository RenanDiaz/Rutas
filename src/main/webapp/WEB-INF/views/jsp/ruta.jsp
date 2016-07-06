<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
<title>Rutas - Vehiculo</title>
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
<c:if test="${not empty ruta}">
  <div class="container">
    <div class="table-responsive">
      <table class="table table-striped">
        <thead>
          <tr>
            <th>ID</th>
            <th>Partida</th>
            <th>Destino</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>${ruta.getId()}</td>
            <td>${ruta.getPartida()}</td>
            <td>${ruta.getDestino()}</td>
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
        <h4 class="modal-title">Editar ruta</h4>
      </div>
      <div class="modal-body">
        <c:if test="${not empty ruta}">
          <div class="form-group">
            <label>ID</label> <input id="id" class="form-control" type="text" disabled value="${ruta.getId()}">
          </div>
          <div class="form-group">
            <label>Partida</label> <input id="partida" class="form-control" type="text" required value="${ruta.getPartida()}">
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

<script src="${jquery}"></script>
<script src="${coreJs}"></script>
<script src="${bootstrapJs}"></script>
<script type="text/javascript">
$("#guardar").click(function() {
  $.ajax({
    url: "${pageContext.request.contextPath}/rutas/editar",
    method: "POST",
    data: {
      id: $("#id").val(),
      partida: $("#partida").val(),
      destino: $("#destino").val()
    },
    success: function() {
      location.reload();
    }
  });
});
</script>
</body>
</html>