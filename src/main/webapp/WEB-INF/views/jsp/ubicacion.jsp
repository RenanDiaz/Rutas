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
      <a class="navbar-brand" href="/Rutas/">Rutas</a>
    </div>
  </div>
</nav>
<br>
<br>
<br>
<div class="container">
  <h2>
    <a href="/Rutas/ubicacion" class="btn btn-info"> <span class="glyphicon glyphicon-triangle-left"></span>
    </a>
    <c:if test="${not empty ubicacion.vehiculo()}">
      ${ubicacion.id()} - ${ubicacion.vehiculo().nombreCorto()}
    </c:if>
    <c:if test="${empty ubicacion.vehiculo()}">
      No encontrado
    </c:if>
  </h2>
</div>
<br>
<c:if test="${not empty ubicacion}">
  <div class="container">
    <div class="row">
      <div class="col-sm-1">
        <c:if test="${ubicacion.id() > 1}">
          <h2>
            <a href="/Rutas/ubicacion/${ubicacion.id() - 1}" class="btn btn-info"><span class="glyphicon glyphicon-menu-left"></span></a>
          </h2>
        </c:if>
      </div>
      <div class="table-responsive col-sm-10">
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
              <td>${ubicacion.ruta().descripcion()}</td>
              <td>${ubicacion.latitud()}</td>
              <td>${ubicacion.longitud()}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="col-sm-1">
        <c:if test="${ubicacion.id() < total}">
          <h2>
            <a href="/Rutas/ubicacion/${ubicacion.id() + 1}" class="btn btn-info"><span class="glyphicon glyphicon-menu-right"></span></a>
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
          <input id="latitud-copy" class="form-control" type="text" disabled value="${ubicacion.latitud()}">
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
          <input id="longitud-copy" class="form-control" type="text" disabled value="${ubicacion.longitud()}">
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
        <c:if test="${not empty vehiculos}">
          <div class="form-group">
            <label>Veh&iacute;culo</label> <select name="placa" id="placa" class="form-control">
              <c:forEach items="${vehiculos}" var="vehiculo">
                <c:if test="${vehiculo.placa() == ubicacion.vehiculo().placa()}">
                  <option value="${vehiculo.placa()}" selected>${vehiculo.nombreCorto()}</option>
                </c:if>
                <c:if test="${vehiculo.placa() != ubicacion.vehiculo().placa()}">
                  <option value="${vehiculo.placa()}">${vehiculo.nombreCorto()}</option>
                </c:if>
              </c:forEach>
            </select>
          </div>
          <div class="form-group">
            <label>Ruta</label> <select name="ruta" id="ruta" class="form-control">
              <c:forEach items="${rutas}" var="ruta">
                <c:if test="${ruta.id() == ubicacion.ruta().id()}">
                  <option value="${ruta.id()}" selected>${ruta.descripcion()}</option>
                </c:if>
                <c:if test="${ruta.id() != ubicacion.ruta().id()}">
                  <option value="${ruta.id()}">${ruta.descripcion()}</option>
                </c:if>
              </c:forEach>
            </select>
          </div>
          <div class="form-group">
            <label>Latitud</label> <input name="latitud" id="latitud" class="form-control" type="text" required value="${ubicacion.latitud()}">
          </div>
          <div class="form-group">
            <label>Longitud</label> <input name="longitud" id="longitud" class="form-control" type="text" required value="${ubicacion.longitud()}">
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
    url: "${pageContext.request.contextPath}/ubicacion/editar/" + $("#placa").val(),
    method: "POST",
    data: {
      id: "${ubicacion.id()}",
      ruta: $("#ruta").val(),
      latitud: $("#latitud").val(),
      longitud: $("#longitud").val()
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