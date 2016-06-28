<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
<title>Rutas - Ubicaciones</title>

<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=0">
<spring:url value="/resources/core/css/hello.css" var="coreCss" />
<spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
<spring:url value="/resources/core/DataTables/datatables.min.css" var="datatablesCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${coreCss}" rel="stylesheet" />
<link href="${datatablesCss}" rel="stylesheet" />
</head>

<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">Ubicaciones</a>
    </div>
  </div>
</nav>

<div class="jumbotron"></div>

<c:if test="${not empty ubicaciones}">
  <div class="container">
    <div class="table-responsive">
      <table class="table table-striped">
        <thead>
          <tr>
            <th>Fecha y hora</th>
            <th>Ruta</th>
            <th>Vehiculo</th>
            <th>Latitud</th>
            <th>Longitud</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${ubicaciones}" var="ubicacion">
            <tr>
              <td>${ubicacion.fechahora()}</td>
              <td>${ubicacion.ruta()}</td>
              <td>${ubicacion.vehiculo().placa()}</td>
              <td>${ubicacion.latitud()}</td>
              <td>${ubicacion.longitud()}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#popUpDeConfirmacion">Agregar</button>
  </div>
</c:if>
<div id="popUpDeConfirmacion" class="modal fade" role="dialog">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Agregar ubicacion</h4>
      </div>
      <div class="modal-body" id="mensaje">
        <c:if test="${not empty vehiculos}">
          <div class="form-group">
            <label>Veh&iacute;culo</label>
            <select name="placa" id="placa" class="form-control">
              <c:forEach items="${vehiculos}" var="vehiculo">
                <option value="${vehiculo.placa()}">${vehiculo.modelo()}</option>
              </c:forEach>
            </select>
          </div>
          <div class="form-group">
            <label>Latitud</label> <input name="latitud" id="latitud" class="form-control" type="text" required>
          </div>
          <div class="form-group">
            <label>Longitud</label> <input name="longitud" id="longitud" class="form-control" type="text" required>
          </div>
        </c:if>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal" id="enviar">Enviar</button>
        <button type="button" class="btn" data-dismiss="modal">Cancelar</button>
      </div>
    </div>
  </div>
</div>

<spring:url value="/resources/core/js/jquery-3.0.0.js" var="jquery" />
<spring:url value="/resources/core/js/hello.js" var="coreJs" />
<spring:url value="/resources/core/js/bootstrap.min.js" var="bootstrapJs" />
<spring:url value="/resources/core/DataTables/datatables.min.js" var="datatablesJs" />

<script src="${jquery}"></script>
<script src="${coreJs}"></script>
<script src="${bootstrapJs}"></script>
<script src="${datatablesJs}"></script>
<script type="text/javascript">
$("#enviar").click(function() {
  $.ajax({
    url : "${pageContext.request.contextPath}/ubicacion/guardar/" + $("#placa").val(),
    method : "POST",
    data : {
      latitud : $("#latitud").val(),
      longitud : $("#longitud").val()
    },
    success: function() {
      console.log("success");
    }
  });
});

$("table").DataTable();
</script>
</body>
</html>