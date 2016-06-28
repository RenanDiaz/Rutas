<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
<title>Rutas - Ubicaci&oacute;n</title>

<spring:url value="/resources/core/css/hello.css" var="coreCss" />
<spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${coreCss}" rel="stylesheet" />
<style type="text/css">
.hidden {
  display: none;
}
</style>
</head>

<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">Veh&iacute;culo</a>
    </div>
  </div>
</nav>

<div class="jumbotron">
  <div class="container">
    <h1>${title}</h1>
    <p>
      <c:if test="${not empty ubicacion.vehiculo()}">
      ${ubicacion.vehiculo().marca()} ${ubicacion.vehiculo().modelo()} de ${ubicacion.vehiculo().anno()}
    </c:if>

      <c:if test="${empty ubicacion.vehiculo()}">
      No encontrado
    </c:if>
    </p>
  </div>
</div>

<c:if test="${not empty ubicacion}">
  <div class="container">
    <div class="table-responsive">
      <table class="table table-striped">
        <thead>
          <tr>
            <th>Fecha y hora</th>
            <th>Ruta</th>
            <th>Latitud</th>
            <th>Longitud</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>${ubicacion.fechahora()}</td>
            <td>${ubicacion.ruta()}</td>
            <td>${ubicacion.latitud()}</td>
            <td>${ubicacion.longitud()}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</c:if>
<div class="container">
  <div class="row">
    <div class="col-md-3">
      <div class="form-group">
        <label>Placa</label> <input name="placa" id="placa" class="form-control" type="text" required>
      </div>
      <div class="form-group">
        <label>Latitud</label> <input name="latitud" id="latitud" class="form-control" type="text" required>
      </div>
      <div class="form-group">
        <label>Longitud</label> <input name="longitud" id="longitud" class="form-control" type="text" required>
      </div>
      <input type="submit" id="enviar" class="btn btn-primary">
    </div>
  </div>
</div>

<spring:url value="/resources/core/css/hello.js" var="coreJs" />
<spring:url value="/resources/core/css/bootstrap.min.js" var="bootstrapJs" />

<script src="${coreJs}"></script>
<script src="${bootstrapJs}"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/core/js/jquery-3.0.0.js"></script>
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
evaluarSelect();
function evaluarSelect() {
  if ($("#marca").val() == 0) {
    $("#oculto").removeClass("hidden");
  } else {
    $("#oculto").addClass("hidden");
  }
}

$("#marca").change(function() {
  evaluarSelect();
});
</script>
</body>
</html>