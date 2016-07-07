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
      <a class="navbar-brand" href="${pageContext.request.contextPath}/">Rutas</a>
    </div>
  </div>
</nav>
<br>
<br>
<br>
<div class="container">
  <h2>
    <a href="${pageContext.request.contextPath}/" class="btn btn-info"> <span class="glyphicon glyphicon-triangle-left"></span>
    </a> Ubicaciones
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
            <tr class="link" id="${ubicacion.getId()}" title="${ubicacion.getId()}">
              <td>${ubicacion.getFechahora()}</td>
              <td>${ubicacion.getRuta().getDescripcion()}</td>
              <td>${ubicacion.getVehiculo().getNombreCorto()}</td>
              <td>${ubicacion.getLatitud()}</td>
              <td>${ubicacion.getLongitud()}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
    <div class="row">
      <div class="col-xs-12 col-sm-2">
        <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#popUpAgregar">
          Agregar <span class="glyphicon glyphicon-plus-sign"></span>
        </button>
      </div>
      <div class="col-xs-12 col-sm-2">
        <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#popUpCalcular">
          Distancia <span class="glyphicon glyphicon-road"></span>
        </button>
      </div>
      <div class="col-xs-12 col-sm-2">
        <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#popUpExportar">
          Exportar <span class="glyphicon glyphicon-export"></span>
        </button>
      </div>
      <div class="col-xs-12 col-sm-2">
        <a class="btn btn-info btn-lg" id="descargar">
          Descargar <span class="glyphicon glyphicon-download-alt"></span>
        </a>
      </div>
      <div class="col-xs-12 col-sm-2">
        <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#popUpMapa">
          Ver mapa <span class="glyphicon glyphicon-map-marker"></span>
        </button>
      </div>
    </div>
    <br>
    <br>
    <div class="row" id="iframe-row" style="display: none;">
      <div class="col-xs-12">
        <iframe id="iframe-mapa" style="width: 100%; height: 300px;"></iframe>
      </div>
    </div>
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
                <option value="${vehiculo.getPlaca()}">${vehiculo.getNombreCorto()}</option>
              </c:forEach>
            </select>
          </div>
          <div class="form-group">
            <label>Ruta</label>
            <select name="ruta" id="ruta" class="form-control">
              <c:forEach items="${rutas}" var="ruta">
                <option value="${ruta.getId()}">${ruta.getDescripcion()}</option>
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
        <button type="button" class="btn btn-primary" data-dismiss="modal" id="agregar">Agregar</button>
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
          <label>Inicio</label> <input id="inicio-calc" class="form-control" type="number" min="${ubicaciones.get(0).getId()}" max="${ubicaciones.get(total - 1).getId()}" required autofocus>
        </div>
        <div class="form-group">
          <label>Fin</label> <input id="fin-calc" class="form-control" type="number" min="${ubicaciones.get(0).getId()}" max="${ubicaciones.get(total - 1).getId()}" required>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal" id="calcular">Calcular</button>
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
            </span> <input id="inicio" class="form-control disableable" type="number" min="${ubicaciones.get(0).getId()}" max="${ubicaciones.get(total - 1).getId()}" required disabled>
          </div>
        </div>
        <div class="form-group">
          <label>Fin</label>
          <div class="input-group">
            <span class="input-group-addon"> <input type="checkbox" class="checkbox" id="cb-fin" title="Si no marca esta casilla se exportará hasta el último registro">
            </span> <input id="fin" class="form-control disableable" type="number" min="${ubicaciones.get(0).getId()}" max="${ubicaciones.get(total - 1).getId()}" required disabled>
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

<div id="popUpMapa" class="modal fade" role="dialog">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Ver ruta en mapa</h4>
      </div>
      <div class="modal-body">
        <c:if test="${not empty vehiculos}">
          <div class="form-group">
            <label>Veh&iacute;culo</label> <select id="placa-mapa" class="form-control" autofocus>
              <c:forEach items="${vehiculos}" var="vehiculo">
                <option value="${vehiculo.getPlaca()}">${vehiculo.getNombreCorto()}</option>
              </c:forEach>
            </select>
          </div>
          <div class="form-group">
            <label>Inicio</label>
            <div class="input-group">
              <span class="input-group-addon"> <input type="checkbox" class="checkbox" id="cb-inicio" title="Si no marca esta casilla se exportará desde el primer regristro" autofocus>
              </span> <input id="inicio-mapa" class="form-control disableable" type="number" min="${ubicaciones.get(0).getId()}" max="${ubicaciones.get(total - 1).getId()}" required disabled>
            </div>
          </div>
          <div class="form-group">
            <label>Fin</label>
            <div class="input-group">
              <span class="input-group-addon"> <input type="checkbox" class="checkbox" id="cb-fin" title="Si no marca esta casilla se exportará hasta el último registro">
              </span> <input id="fin-mapa" class="form-control disableable" type="number" min="${ubicaciones.get(0).getId()}" max="${ubicaciones.get(total - 1).getId()}" required disabled>
            </div>
          </div>
        </c:if>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal" id="verMapa">Ver</button>
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
<spring:url value="/resources/core/DataTables/datatables.min.js" var="datatablesJs" />

<script src="${jquery}"></script>
<script src="${coreJs}"></script>
<script src="${bootstrapJs}"></script>
<script src="${datatablesJs}"></script>
<script type="text/javascript">
$("table").DataTable({
	"order": [[ 0, "desc" ]]
});

$("#descargar").attr("disabled", true);

$("#agregar").click(function() {
  var d = new Date();
  var fecha = d.getTime();
  $.ajax({
    url: "${pageContext.request.contextPath}/ubicacion/agregar/" + $("#placa").val(),
    method: "POST",
    data: {
      fecha: fecha,
      ruta: $("#ruta").val(),
      latitud: $("#latitud").val(),
      longitud: $("#longitud").val()
    },
    success: function() {
      location.reload();
    }
  });
});

$("#calcular").click(function() {
  $.ajax({
    url: "${pageContext.request.contextPath}/ubicacion/calcular",
    method: "POST",
    data: {
      inicio: $("#inicio-calc").val(),
      fin: $("#fin-calc").val()
    },
    success: function(data) {
      alert("Distancia recorrida: " + data + "m");
    }
  });
});

$("#exportar").click(function() {
  var primera = ${ubicaciones.get(0).getId()};
  var ultima = ${ubicaciones.get(total - 1).getId()}
  var inicio = $("#cb-inicio").prop("checked") ? $("#inicio").val() : primera;
  var fin = $("#cb-fin").prop("checked") ? $("#fin").val() : ultima;
  $.ajax({
    url: "${pageContext.request.contextPath}/ubicacion/exportar",
    method: "POST",
    data: {
      inicio: inicio,
      fin: fin
    },
    success: function(url) {
      if(url != "error")
      {
        var filename = url.substring(url.lastIndexOf("/") + 1).split("?")[0];
        $("#descargar").attr("disabled", false);
        $("#descargar").prop("download", filename);
        $("#descargar").prop("href", "${pageContext.request.contextPath}" + url);
      }
    }
  });
});

$("#verMapa").click(function() {
  var primera = ${ubicaciones.get(0).getId()};
  var ultima = ${ubicaciones.get(total - 1).getId()}
  var inicio = $("#cb-inicio-mapa").prop("checked") ? $("#inicio-mapa").val() : primera;
  var fin = $("#cb-fin-mapa").prop("checked") ? $("#fin-mapa").val() : ultima;
  $("#iframe-mapa").attr("src", "${pageContext.request.contextPath}/mapa/" + $("#placa-mapa").val() +"?inicio=" + inicio + "&fin=" + fin);
  $("#iframe-row").show();
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