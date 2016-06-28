<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
<title>Rutas - Vehiculos</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=0">
<spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
<spring:url value="/resources/core/css/common.css" var="commonCss" />
<spring:url value="/resources/core/DataTables/datatables.min.css" var="datatablesCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${commonCss}" rel="stylesheet" />
<link href="${datatablesCss}" rel="stylesheet" />
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
    <a href="/Rutas/" class="btn btn-info">
      <span class="glyphicon glyphicon-menu-left"></span>
    </a>
    Veh&iacute;culos
  </h2>
</div>
<br>
<c:if test="${not empty vehiculos}">
  <div class="container">
    <div class="table-responsive">
      <table class="table table-striped table-hover">
        <thead>
          <tr>
            <th>Placa</th>
            <th>Marca</th>
            <th>Modelo</th>
            <th>A&ntilde;o</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${vehiculos}" var="vehiculo">
            <tr class="link" id="${vehiculo.placa()}">
              <td>${vehiculo.placa()}</td>
              <td>${vehiculo.marca()}</td>
              <td>${vehiculo.modelo()}</td>
              <td>${vehiculo.anno()}</td>
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
        <h4 class="modal-title">Agregar veh&iacute;culo</h4>
      </div>
      <div class="modal-body" id="mensaje">
        <c:if test="${not empty marcas}">
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
<spring:url value="/resources/core/DataTables/datatables.min.js" var="datatablesJs" />

<script src="${jquery}"></script>
<script src="${coreJs}"></script>
<script src="${bootstrapJs}"></script>
<script src="${datatablesJs}"></script>
<script type="text/javascript">
$("table").DataTable();

$("#enviar").click(function() {
  $.ajax({
    url: "${pageContext.request.contextPath}/bus/agregar",
    method: "POST",
    data: {
      placa: $("#placa").val(),
      marca: $("#marca").val(),
      nombreMarca: $("#nombreMarca").val(),
      modelo: $("#modelo").val(),
      anno: $("#anno").val()
    },
    success: function() {
    	console.log("success");
    }
  });
});

$("#regresar").click(function() {
  window.location.href = "/Rutas/";
});

$("#marca").change(function() {
  if ($(this).val() == 0) {
    $("#oculto").removeClass("hidden");
  } else {
    $("#oculto").addClass("hidden");
  }
});

$(".link").click(function() {
  var id = $(this).prop('id');
  window.location.href = "/Rutas/buses/" + id;
});
</script>
</body>
</html>