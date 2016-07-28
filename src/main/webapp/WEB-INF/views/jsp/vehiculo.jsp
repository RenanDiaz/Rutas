<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="es">
<head>
<title>Rutas - Veh&iacute;culo</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=0">
<spring:url value="/resources/core/js/jquery-3.0.0.js" var="jquery" />
<spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
<spring:url value="/resources/core/js/bootstrap.min.js" var="bootstrapJs" />
<spring:url value="/resources/core/css/common.css" var="commonCss" />
<spring:url value="/resources/core/js/common.js" var="commonJs" />
<spring:url value="/resources/core/DataTables/datatables.min.css" var="datatablesCss" />
<spring:url value="/resources/core/DataTables/datatables.min.js" var="datatablesJs" />

<script src="${jquery}"></script>
<link href="${bootstrapCss}" rel="stylesheet" />
<script src="${bootstrapJs}"></script>
<link href="${commonCss}" rel="stylesheet" />
<script src="${commonJs}"></script>
<link href="${datatablesCss}" rel="stylesheet" />
<script src="${datatablesJs}"></script>
<style type="text/css">
.hidden {
  display: none;
}
</style>
</head>
<body>
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
      <a href="/Rutas/vehiculos/${tipo}" class="btn btn-info"> <span class="glyphicon glyphicon-triangle-left"></span>
      </a> ${empty vehiculo ? "No encontrado" : vehiculo.getNombre()}
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
              <td>${vehiculo.getPlaca()}</td>
              <td>${vehiculo.getMarca().getNombre()}</td>
              <td>${vehiculo.getModelo()}</td>
              <td>${vehiculo.getAnno()}</td>
              <td>${vehiculo.getTipo()}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="row" id="botones">
        <div class="col-xs-6 col-sm-2">
          <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#popUpDeEditar">
            Editar <span class="glyphicon glyphicon-edit"></span>
          </button>
        </div>
      </div>
      <hr>
      <h3>Asignaciones</h3>
      <div class="table-responsive">
        <table id="asignaciones" class="table table-striped table-hover">
          <thead>
            <tr>
              <th>Ruta</th>
              <th>Partida</th>
              <th>Llegada</th>
            </tr>
          </thead>
          <tbody>
            <c:if test="${not empty asignaciones}">
              <c:forEach items="${asignaciones}" var="asignacion">
                <tr class="link" id="${asignacion.getId()}" title="${asignacion.getId()}">
                  <td>${asignacion.getRuta().getDescripcion()}</td>
                  <td><fmt:formatDate value="${asignacion.getFechahoraDePartida()}" pattern="yyyy-MM-dd HH:mm"/></td>
                  <td><fmt:formatDate value="${asignacion.getFechahoraDeLlegada()}" pattern="yyyy-MM-dd HH:mm"/></td>
                </tr>
              </c:forEach>
            </c:if>
          </tbody>
        </table>
      </div>
      <hr>
      <h3>Ubicaciones</h3>
      <div class="table-responsive">
        <table id="ubicaciones" class="table table-striped table-hover">
          <thead>
            <tr>
              <th>Fecha y hora</th>
              <th>Ruta</th>
              <th>Latitud</th>
              <th>Longitud</th>
              <th>Altitud</th>
            </tr>
          </thead>
          <tbody>
            <c:if test="${not empty ubicaciones}">
              <c:forEach items="${ubicaciones}" var="ubicacion">
                <tr class="link" id="${ubicacion.getId()}" title="${ubicacion.getId()}">
                <td><fmt:formatDate value="${ubicacion.getFechahora()}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                  <td>${ubicacion.getAsignacion().getRuta().getDescripcion()}</td>
                  <td>${ubicacion.getLatitud()}</td>
                  <td>${ubicacion.getLongitud()}</td>
                  <td>${ubicacion.getAltitud()}</td>
                </tr>
              </c:forEach>
            </c:if>
          </tbody>
        </table>
      </div>
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
              <label>Placa</label> <input name="placa" id="placa" class="form-control" type="text" disabled value="${vehiculo.getPlaca()}">
            </div>
            <div class="form-group">
              <label>Marca</label> <select name="marca" id="marca" class="form-control">
                <c:forEach items="${marcas}" var="marca">
                  <option value="${marca.getId()}" ${marca.getNombre() == vehiculo.getMarca().getNombre() ? "selected" : ""}>${marca.getNombre()}</option>
                </c:forEach>
                <option value="0">Otra</option>
              </select>
              <div id="oculto" class="hidden">
                <br> <input name="nombreMarca" id="nombreMarca" class="form-control">
              </div>
            </div>
            <div class="form-group">
              <label>Modelo</label> <input name="modelo" id="modelo" class="form-control" type="text" required value="${vehiculo.getModelo()}">
            </div>
            <fmt:formatDate var="year" value="<%=new java.util.Date()%>" pattern="yyyy"/>
            <c:set var="max" value="${year + 1}" />
            <c:set var="min" value="${year - 98}" />
            <div class="form-group">
              <label>A&ntilde;o</label> <input name="anno" id="anno" class="form-control" type="number" max="${max}" min="${min}" required value="${vehiculo.getAnno()}">
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
  $("#asignaciones").DataTable({
  	"order": [[ 1, "desc" ]]
  });
  
  $("#ubicaciones").DataTable({
  	"order": [[ 0, "desc" ]]
  });
  
  $("#guardar").click(function() {
    $.ajax({
      url: "${pageContext.request.contextPath}/vehiculos/${tipo}/editar",
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
  
  $('body').delegate(".link", "click", function() {
    var id = $(this).prop("id");
    window.location.href = "/Rutas/asignacion/" + id;
  });
  </script>
</body>
</html>