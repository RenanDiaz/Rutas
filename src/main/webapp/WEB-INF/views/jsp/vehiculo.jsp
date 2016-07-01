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
    <a href="/Rutas/${tipo}" class="btn btn-info">
      <span class="glyphicon glyphicon-triangle-left"></span>
    </a>
    <c:if test="${not empty vehiculo}">
      ${vehiculo.nombre()}
    </c:if>
    <c:if test="${empty vehiculo}">
      No encontrado
    </c:if>
  </h2>
</div>
<br>
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
  <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#popUpDeEditar">Editar <span class="glyphicon glyphicon-edit"></span></button>
  </div>
</c:if>

<div id="popUpDeEditar" class="modal fade" role="dialog">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Editar veh&iacute;culo</h4>
      </div>
      <div class="modal-body" id="mensaje">
        <c:if test="${not empty marcas}">
          <div class="form-group">
            <label>Placa</label> <input name="placa" id="placa" class="form-control" type="text" disabled value="${vehiculo.placa()}">
          </div>
          <div class="form-group">
            <label>Marca</label> <select name="marca" id="marca" class="form-control">
              <c:forEach items="${marcas}" var="marca">
                <c:if test="${marca.nombre() == vehiculo.marca()}">
                  <option value="${marca.id()}" selected>${marca.nombre()}</option>
                </c:if>
                <c:if test="${marca.nombre() != vehiculo.marca()}">
                  <option value="${marca.id()}">${marca.nombre()}</option>
                </c:if>
              </c:forEach>
              <option value="0">Otra</option>
            </select>
            <div id="oculto" class="hidden">
              <br>
              <input name="nombreMarca" id="nombreMarca" class="form-control">
            </div>
          </div>
          <div class="form-group">
            <label>Modelo</label> <input name="modelo" id="modelo" class="form-control" type="text" required value="${vehiculo.modelo()}">
          </div>
          <div class="form-group">
            <label>A&ntilde;o</label> <input name="anno" id="anno" class="form-control" type="number" max="2017" min="1918" required value="${vehiculo.anno()}">
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
<spring:url value="/resources/core/js/common.js" var="coreJs" />
<spring:url value="/resources/core/js/bootstrap.min.js" var="bootstrapJs" />

<script src="${jquery}"></script>
<script src="${coreJs}"></script>
<script src="${bootstrapJs}"></script>
<script type="text/javascript">
$("#enviar").click(function() {
  $.ajax({
    url: "${pageContext.request.contextPath}/${tipo}/editar",
    method: "POST",
    data: {
      placa: $("#placa").val(),
      marca: $("#marca").val(),
      nombreMarca: $("#nombreMarca").val(),
      modelo: $("#modelo").val(),
      anno: $("#anno").val()
    },
    success: function() {
      location.reload();
    }
  });
});

$("#marca").change(function() {
  if ($(this).val() == 0) {
    $("#oculto").removeClass("hidden");
  } else {
    $("#oculto").addClass("hidden");
  }
});
</script>
</body>
</html>