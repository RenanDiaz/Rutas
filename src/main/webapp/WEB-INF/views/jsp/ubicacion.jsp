<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="es">
<head>
<title>Rutas - Ubicaci&oacute;n</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=0">
<spring:url value="/resources/core/js/jquery-3.0.0.js" var="jquery" />
<spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
<spring:url value="/resources/core/js/bootstrap.min.js" var="bootstrapJs" />
<spring:url value="/resources/core/css/common.css" var="commonCss" />
<spring:url value="/resources/core/js/common.js" var="commonJs" />

<script src="${jquery}"></script>
<link href="${bootstrapCss}" rel="stylesheet" />
<script src="${bootstrapJs}"></script>
<link href="${commonCss}" rel="stylesheet" />
<script src="${commonJs}"></script>
</head>

<body>
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
      ${ubicacion.getId()}${empty ubicacion.getAsignacion().getVehiculo() ? "No encontrado" : " - "}${ubicacion.getAsignacion().getVehiculo().getNombreCorto()}
    </h2>
  </div>
  <br>
  <c:if test="${not empty ubicacion}">
    <div class="container">
      <div class="row">
        <div class="col-sm-1">
          <h2>
            <a href="${pageContext.request.contextPath}/ubicacion/${ubicacion.getId() - 1}" class="btn btn-info" ${ubicacion.getId() > 1 ? "" : "disabled"}><span class="glyphicon glyphicon-menu-left"></span></a>
          </h2>
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
                <td><fmt:formatDate value="${ubicacion.getFechahora()}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                <td>${ubicacion.getAsignacion().getDescripcion()}</td>
                <td>${ubicacion.getLatitud()}</td>
                <td>${ubicacion.getLongitud()}</td>
                <td>${ubicacion.getAltitud()}</td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="col-sm-1">
          <h2>
            <a href="${pageContext.request.contextPath}/ubicacion/${ubicacion.getId() + 1}" class="btn btn-info" ${ubicacion.getId() < total ? "" : "disabled"}><span class="glyphicon glyphicon-menu-right"></span></a>
          </h2>
        </div>
      </div>
      <div class="row">
        <div class="col-xs-6 col-sm-2">
          <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#popUpDeEditar">
            Editar <span class="glyphicon glyphicon-edit"></span>
          </button>
        </div>
        <div class="col-xs-6 col-sm-2">
          <button type="button" class="btn btn-info btn-lg" id="verMapa">
            Ver mapa <span class="glyphicon glyphicon-map-marker"></span>
          </button>
        </div>
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
    <br> <br>
    <div class="row" id="iframe-row" style="display: none;">
      <div class="col-xs-12">
        <iframe id="iframe-mapa" style="width: 100%; height: 300px;"></iframe>
      </div>
      <br>
      <div class="col-xs-12">
        <a href="#" target="_blank" id="link-mapa">Abrir en una ventana aparte</a>
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

  $("#verMapa").click(function() {
    var ruta = "${pageContext.request.contextPath}/mapa/punto/${ubicacion.getId()}";
    $("#iframe-mapa").attr("src", ruta);
    $("#link-mapa").attr("href", ruta);
    $("#iframe-row").show();
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