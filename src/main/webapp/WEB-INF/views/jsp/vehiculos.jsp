<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
<title>Rutas - Vehiculos</title>

<spring:url value="/resources/core/css/hello.css" var="coreCss" />
<spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${coreCss}" rel="stylesheet" />
</head>

<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">Veh&iacute;culos</a>
    </div>
  </div>
</nav>

<div class="jumbotron">
  <div class="container">
    <h1>${title}</h1>
    <p>
      Veh&iacute;culos
    </p>
  </div>
</div>

<c:if test="${not empty vehiculos}">
  <div class="container">
    <div class="table-responsive">
      <table class="table table-striped">
        <thead>
          <tr>
            <th>Placa</th>
            <th>Marca</th>
            <th>Modelo</th>
            <th>A&ntilde;o</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${vehiculos.lista()}" var="vehiculo">
            <tr>
              <td>${vehiculo.placa()}</td>
              <td>${vehiculo.marca()}</td>
              <td>${vehiculo.modelo()}</td>
              <td>${vehiculo.anno()}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
</c:if>

<spring:url value="/resources/core/css/hello.js" var="coreJs" />
<spring:url value="/resources/core/css/bootstrap.min.js" var="bootstrapJs" />

<script src="${coreJs}"></script>
<script src="${bootstrapJs}"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript">
function ejecutar() {
  $.ajax({
    url: "agregar",
    method: "POST",
    data: {
      placa: $("#placa").val(),
      marca: $("#marca").val(),
      nombreMarca: $("#nombreMarca"),
      modelo: $("#modelo").val(),
      anno: $("#anno").val()
    }
  });
}
</script>
</body>
</html>