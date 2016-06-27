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
</head>

<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">Spring 3 MVC Project</a>
    </div>
  </div>
</nav>

<div class="jumbotron">
  <div class="container">
    <h1>${title}</h1>
    <p>
      <c:if test="${not empty vehiculo}">
      Hello ${vehiculo.modelo()}
    </c:if>

      <c:if test="${empty vehiculo}">
      Welcome Welcome!
    </c:if>
    </p>
    <p>
      <a class="btn btn-primary btn-lg" href="#" role="button">Learn more</a>
    </p>
  </div>
</div>

<div class="container">

  <div class="row">
    <div class="col-md-4">
      <h2>Heading</h2>
      <p>ABC</p>
      <p>
        <a class="btn btn-default" href="#" role="button">View details</a>
      </p>
    </div>
    <div class="col-md-4">
      <h2>Heading</h2>
      <p>ABC</p>
      <p>
        <a class="btn btn-default" href="#" role="button">View details</a>
      </p>
    </div>
    <div class="col-md-4">
      <h2>Heading</h2>
      <p>ABC</p>
      <p>
        <a class="btn btn-default" href="#" role="button">View details</a>
      </p>
    </div>
  </div>
  <div class="row">
    <div class="col-md-4">
      <div class="form-group">
        <label>Placa</label> <input name="placa" class="form-control" type="text" required>
      </div>
      <div class="form-group">
        <label>Marca</label> <input name="marca" class="form-control" type="text" required>
      </div>
      <div class="form-group">
        <label>Modelo</label> <input name="modelo" class="form-control" type="text" required>
      </div>
      <div class="form-group">
        <label>A&ntilde;o</label> <input name="anno" class="form-control" type="number" max="2017" min="1918" required>
      </div>
      <input type="submit" class="btn btn-primary" onclick="javascript: ejecutar();">
    </div>
  </div>


  <hr>
  <footer>
    <p>&copy; Mkyong.com 2015</p>
  </footer>
</div>

<spring:url value="/resources/core/css/hello.js" var="coreJs" />
<spring:url value="/resources/core/css/bootstrap.min.js" var="bootstrapJs" />

<script src="${coreJs}"></script>
<script src="${bootstrapJs}"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript">
function ejecutar() {
  $.ajax({
    url: "agregar/",
    method: "POST",
    data: {
      placa: $('[name="placa"]').val(),
      marca: $('[name="marca"]').val(),
      modelo: $('[name="modelo"]').val(),
      anno: $('[name="anno"]').val()
    }
  });
}
</script>
</body>
</html>