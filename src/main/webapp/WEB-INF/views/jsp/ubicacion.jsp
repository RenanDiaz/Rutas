<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
<title>Rutas - Ubicaci&oacute;n</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=0">

<spring:url value="/resources/core/css/hello.css" var="coreCss" />
<spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${coreCss}" rel="stylesheet" />
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

<spring:url value="/resources/core/js/jquery-3.0.0.js" var="jquery" />
<spring:url value="/resources/core/js/hello.js" var="coreJs" />
<spring:url value="/resources/core/js/bootstrap.min.js" var="bootstrapJs" />

<script src="${jquery}"></script>
<script src="${coreJs}"></script>
<script src="${bootstrapJs}"></script>
</body>
</html>