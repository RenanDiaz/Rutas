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

      <c:if test="${empty vehiculo}">
      Nuevo veh&iacute;culo
    </c:if>
    </p>
  </div>
</div>

<c:if test="${not empty marcas}">
  <div class="container">
    <div class="row">
      <div class="col-md-3">
        <div class="form-group">
          <label>Placa</label> <input name="placa" id="placa" class="form-control" type="text" required>
        </div>
        <div class="form-group">
          <label>Marca</label> <select name="marca" id="marca" class="form-control">
            <c:forEach items="${marcas}" var="marca">
              <option value="${marca.id()}">${marca.nombre()}</option>
            </c:forEach>
            <option value="0">Otra</option>
          </select>
          <div id="oculto" class="hidden">
            <br>
            <input name="nombreMarca" id="nombreMarca" class="form-control">
          </div>
        </div>
        <div class="form-group">
          <label>Modelo</label> <input name="modelo" id="modelo" class="form-control" type="text" required>
        </div>
        <div class="form-group">
          <label>A&ntilde;o</label> <input name="anno" id="anno" class="form-control" type="number" max="2017" min="1918" required>
        </div>
        <input type="submit" id="enviar" class="btn btn-primary">
      </div>
    </div>
  </div>
</c:if>

<spring:url value="/resources/core/css/hello.js" var="coreJs" />
<spring:url value="/resources/core/css/bootstrap.min.js" var="bootstrapJs" />

<script src="${coreJs}"></script>
<script src="${bootstrapJs}"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/core/js/jquery-3.0.0.js"></script>
<script type="text/javascript">
$("#enviar").click(function() {
  $.ajax({
    url : "${pageContext.request.contextPath}/bus/agregar",
    method : "POST",
    data : {
      placa : $("#placa").val(),
      marca : $("#marca").val(),
      nombreMarca : $("#nombreMarca").val(),
      modelo : $("#modelo").val(),
      anno : $("#anno").val()
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