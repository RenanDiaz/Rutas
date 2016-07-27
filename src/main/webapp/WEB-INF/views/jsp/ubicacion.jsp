<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
<title>Rutas - Ubicaci&oacute;n</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=0">
<spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
<spring:url value="/resources/core/css/common.css" var="commonCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${commonCss}" rel="stylesheet" />
</head>

<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="${pageContext.request.contextPath}/">Rutas</a>
    </div>
  </div>
</nav>
<br>
<br>
<br>
<div class="container">
  <h2>
    <a href="${pageContext.request.contextPath}/ubicacion" class="btn btn-info"> <span class="glyphicon glyphicon-triangle-left"></span>
    </a>
    <c:if test="${not empty ubicacion.getAsignacion().getVehiculo()}">
      ${ubicacion.getId()} - ${ubicacion.getAsignacion().getVehiculo().getNombreCorto()}
    </c:if>
    <c:if test="${empty ubicacion.getAsignacion().getVehiculo()}">
      No encontrado
    </c:if>
  </h2>
</div>
<br>
<c:if test="${not empty ubicacion}">
  <div class="container">
    <div class="row">
      <div class="col-sm-1">
        <c:if test="${ubicacion.getId() > 1}">
          <h2>
            <a href="${pageContext.request.contextPath}/ubicacion/${ubicacion.getId() - 1}" class="btn btn-info"><span class="glyphicon glyphicon-menu-left"></span></a>
          </h2>
        </c:if>
      </div>
      <div class="table-responsive col-sm-10">
        <table class="table table-striped">
          <thead>
            <tr>
              <th>Fecha y hora</th>
              <th>Asignaci&oacute;n</th>
              <th>Latitud</th>
              <th>Longitud</th>
              <th>Altitud</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>${ubicacion.getFechahora()}</td>
              <td>${ubicacion.getAsignacion().getDescripcion()}</td>
              <td>${ubicacion.getLatitud()}</td>
              <td>${ubicacion.getLongitud()}</td>
              <td>${ubicacion.getAltitud()}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="col-sm-1">
        <c:if test="${ubicacion.getId() < total}">
          <h2>
            <a href="${pageContext.request.contextPath}/ubicacion/${ubicacion.getId() + 1}" class="btn btn-info"><span class="glyphicon glyphicon-menu-right"></span></a>
          </h2>
        </c:if>
      </div>
    </div>
    <div class="row">
      <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#popUpDeEditar">Editar <span class="glyphicon glyphicon-edit"></span></button>
    </div>
    <br>
    <div class="row">
      <label>Latitud</label>
    </div>
    <div class="row">
      <div class="col-sm-3">
        <div class="form-group">
          <input id="latitud-copy" class="form-control" type="text" disabled value="${ubicacion.getLatitud()}">
        </div>
      </div>
      <div class="col-sm-1">
        <button id="btn-latitud-copy" class="btn btn-info btn-copy">
          <span class="glyphicon glyphicon-copy"></span>
        </button>
      </div>
    </div>
    <div class="row">
      <label>Longitud</label>
    </div>
    <div class="row">
      <div class="col-sm-3">
        <div class="form-group">
          <input id="longitud-copy" class="form-control" type="text" disabled value="${ubicacion.getLongitud()}">
        </div>
      </div>
      <div class="col-sm-1">
        <button id="btn-longitud-copy" class="btn btn-info btn-copy">
          <span class="glyphicon glyphicon-copy"></span>
        </button>
      </div>
    </div>
  </div>
</c:if>

<div id="popUpDeEditar" class="modal fade" role="dialog">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Editar ubicaci&oacute;n</h4>
      </div>
      <div class="modal-body" id="mensaje">
        <c:if test="${not empty asignaciones}">
          <div class="form-group">
            <label>Asignacion</label> <select name="asignacion" id="asignacion" class="form-control">
              <c:forEach items="${asignaciones}" var="asignacion">
                <option value="${asignacion.getId()}" ${asignacion.getId() == ubicacion.getAsignacion().getId() ? "selected" : ""}>${asignacion.getDescripcion()}</option>
              </c:forEach>
            </select>
          </div>
          <div class="form-group">
            <label>Latitud</label> <input id="latitud" class="form-control" type="text" required value="${ubicacion.getLatitud()}">
          </div>
          <div class="form-group">
            <label>Longitud</label> <input id="longitud" class="form-control" type="text" required value="${ubicacion.getLongitud()}">
          </div>
          <div class="form-group">
            <label>Altitud</label> <input id="altitud" class="form-control" type="text" required value="${ubicacion.getAltitud()}">
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
    url: "${pageContext.request.contextPath}/ubicacion/editar",
    method: "POST",
    data: {
      id: "${ubicacion.getId()}",
      asignacion: $("#asignacion").val(),
      latitud: $("#latitud").val(),
      longitud: $("#longitud").val(),
      altitud: $("#altitud").val()
    },
    success: function() {
      location.reload();
    }
  });
});

$(".btn-copy").click(function() {
  var nombreDelBoton = $(this).prop("id");
  var nombreDelCampo = nombreDelBoton.replace("btn-", "#");
  var valor = $(nombreDelCampo).val();
  copyToClipboard(valor);
});

function copyToClipboard(element) {
  var $temp = $("<input>")
  $("body").append($temp);
  $temp.val(element).select();
  document.execCommand("copy");
  $temp.remove();
}
</script>
</body>
</html>