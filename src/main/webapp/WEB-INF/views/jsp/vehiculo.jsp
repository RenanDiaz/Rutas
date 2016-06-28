<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
<title>Rutas - Vehiculo</title>

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
      <c:if test="${not empty vehiculo}">
        ${vehiculo.marca()} ${vehiculo.modelo()} de ${vehiculo.anno()}
      </c:if>
    </p>
  </div>
</div>

<c:if test="${not empty vehiculo}">
  <div class="container">
    <div class="table-responsive">
      <table class="table table-striped">
        <thead>
          <tr>
            <th>Placa</th>
            <th>Marca</th>
            <th>Modelo</th>
            <th>A&ntilde;o</th>
            <th>Tipo</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>${vehiculo.placa()}</td>
            <td>${vehiculo.marca()}</td>
            <td>${vehiculo.modelo()}</td>
            <td>${vehiculo.anno()}</td>
            <td>${vehiculo.tipo()}</td>
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