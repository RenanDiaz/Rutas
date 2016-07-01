<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
<title>Rutas - Ubicaciones</title>
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
      <span class="glyphicon glyphicon-triangle-left"></span>
    </a>
    Ubicaciones
  </h2>
</div>
<br>
<c:if test="${not empty ubicaciones}">
  <div class="container">
    <div class="table-responsive">
      <table class="table table-striped">
        <thead>
          <tr>
            <th>Fecha y hora</th>
            <th>Ruta</th>
            <th>Vehiculo</th>
            <th>Latitud</th>
            <th>Longitud</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${ubicaciones}" var="ubicacion">
            <tr class="link" id="${ubicacion.id()}" title="${ubicacion.id()}">
              <td>${ubicacion.fechahora()}</td>
              <td>${ubicacion.ruta().descripcion()}</td>
              <td>${ubicacion.vehiculo().nombreCorto()}</td>
              <td>${ubicacion.latitud()}</td>
              <td>${ubicacion.longitud()}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#popUpAgregar">Agregar <span class="glyphicon glyphicon-plus-sign"></span></button>
  <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#popUpExportar">Exportar <span class="glyphicon glyphicon-export"></span></button>
  <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#popUpCalcular">Calcular <span class="glyphicon glyphicon-road"></span></button>
  </div>
</c:if>

<div id="popUpAgregar" class="modal fade" role="dialog">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Agregar ubicaci&oacute;n</h4>
      </div>
      <div class="modal-body">
        <c:if test="${not empty vehiculos}">
          <div class="form-group">
            <label>Veh&iacute;culo</label>
            <select name="placa" id="placa" class="form-control" autofocus>
              <c:forEach items="${vehiculos}" var="vehiculo">
                <option value="${vehiculo.placa()}">${vehiculo.modelo()}</option>
              </c:forEach>
            </select>
          </div>
          <div class="form-group">
            <label>Ruta</label>
            <select name="ruta" id="ruta" class="form-control">
              <c:forEach items="${rutas}" var="ruta">
                <option value="${ruta.id()}">${ruta.descripcion()}</option>
              </c:forEach>
            </select>
          </div>
          <div class="form-group">
            <label>Latitud</label> <input id="latitud" class="form-control" type="text" required>
          </div>
          <div class="form-group">
            <label>Longitud</label> <input id="longitud" class="form-control" type="text" required>
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

<div id="popUpExportar" class="modal fade" role="dialog">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Exportar ubicaciones</h4>
      </div>
      <div class="modal-body">
        <div class="form-group">
          <label>Inicio</label>
          <div class="input-group">
            <span class="input-group-addon"> <input type="checkbox" class="checkbox" id="cb-inicio" title="Si no marca esta casilla se exportará desde el primer regristro" autofocus>
            </span> <input id="inicio" class="form-control disableable" type="number" min="${ubicaciones.get(0).id()}" max="${ubicaciones.get(total - 1).id()}" required disabled>
          </div>
        </div>
        <div class="form-group">
          <label>Fin</label>
          <div class="input-group">
            <span class="input-group-addon"> <input type="checkbox" class="checkbox" id="cb-fin" title="Si no marca esta casilla se exportará hasta el último registro">
            </span> <input id="fin" class="form-control disableable" type="number" min="${ubicaciones.get(0).id()}" max="${ubicaciones.get(total - 1).id()}" required disabled>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal" id="exportar">Exportar</button>
        <button type="button" class="btn" data-dismiss="modal">Cancelar</button>
      </div>
    </div>
  </div>
</div>

<div id="popUpCalcular" class="modal fade" role="dialog">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Calcular distancia</h4>
      </div>
      <div class="modal-body">
        <div class="form-group">
          <label>Inicio</label> <input id="inicio-calc" class="form-control" type="number" min="${ubicaciones.get(0).id()}" max="${ubicaciones.get(total - 1).id()}" required autofocus>
        </div>
        <div class="form-group">
          <label>Fin</label> <input id="fin-calc" class="form-control" type="number" min="${ubicaciones.get(0).id()}" max="${ubicaciones.get(total - 1).id()}" required>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal" id="calcular">Calcular</button>
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
$("table").DataTable({
	"order": [[ 0, "desc" ]]
});

$("#enviar").click(function() {
  $.ajax({
    url: "${pageContext.request.contextPath}/ubicacion/guardar/" + $("#placa").val(),
    method: "POST",
    data: {
      ruta: $("#ruta").val(),
      latitud: $("#latitud").val(),
      longitud: $("#longitud").val()
    },
    success: function() {
      location.reload();
    }
  });
});

$("#exportar").click(function() {
  var primera = ${ubicaciones.get(0).id()};
  var ultima = ${ubicaciones.get(total - 1).id()}
  var inicio = $("#cb-inicio").prop("checked") ? $("#inicio").val() : primera;
  var fin = $("#cb-fin").prop("checked") ? $("#fin").val() : ultima;
  $.ajax({
    url: "${pageContext.request.contextPath}/ubicacion/exportar",
    method: "POST",
    data: {
      inicio: inicio,
      fin: fin
    },
    success: function(data) {
      //saveFile("${pageContext.request.contextPath}/resources/files/route.xml");
      saveToDisk("${pageContext.request.contextPath}/resources/files/route.xml", "route.xml");
    }
  });
});

function saveFile(url) {
  var filename = url.substring(url.lastIndexOf("/") + 1).split("?")[0];
  var xhr = new XMLHttpRequest();
  xhr.responseType = 'blob';
  xhr.onload = function() {
    var a = document.createElement('a');
    a.href = window.URL.createObjectURL(xhr.response); // xhr.response is a blob
    a.download = filename; // Set the file name.
    a.style.display = 'none';
    document.body.appendChild(a);
    a.click();
    delete a;
  };
  xhr.open('GET', url);
  xhr.send();
}

function saveToDisk(fileURL, fileName) {
  // for non-IE
  if (!window.ActiveXObject) {
      var save = document.createElement('a');
      save.href = fileURL;
      save.target = '_blank';
      save.download = fileName || 'unknown';

      var event = document.createEvent('Event');
      event.initEvent('click', true, true);
      save.dispatchEvent(event);
      (window.URL || window.webkitURL).revokeObjectURL(save.href);
  }

  // for IE
  else if ( !! window.ActiveXObject && document.execCommand)     {
      var _window = window.open(fileURL, '_blank');
      _window.document.close();
      _window.document.execCommand('SaveAs', true, fileName || fileURL)
      _window.close();
  }
}

$("#calcular").click(function() {
  $.ajax({
    url: "${pageContext.request.contextPath}/ubicacion/calcular",
    method: "POST",
    data: {
      inicio: $("#inicio-calc").val(),
      fin: $("#fin-calc").val()
    },
    success: function(data) {
      alert(data);
      location.reload();
    }
  });
});

$(".checkbox").change(function() {
  var id = $(this).prop("id").replace("cb-", "#");
  var $input = $(id);
  $input.prop("disabled", !$(this).prop("checked"));
  $input.focus();
});

$('.modal').on('hidden.bs.modal', function () {
  $(".modal input").val("");
  $(".modal input[type='checkbox']").prop("checked", false);
  $(".disableable").prop("disabled", true);
  $(".modal select option:first").prop("selected", true);
})

$('body').delegate(".link", "click", function() {
  var id = $(this).prop("id");
  window.location.href = "/Rutas/ubicacion/" + id;
});
</script>
</body>
</html>