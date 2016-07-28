<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="es">
<head>
<title>Rutas - ${titulo}</title>
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
      <a href="/Rutas/" class="btn btn-info"> <span class="glyphicon glyphicon-triangle-left"></span>
      </a> ${titulo}
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
              <tr class="link" id="${vehiculo.getPlaca()}">
                <td>${vehiculo.getPlaca()}</td>
                <td>${vehiculo.getMarca().getNombre()}</td>
                <td>${vehiculo.getModelo()}</td>
                <td>${vehiculo.getAnno()}</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
      <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#popUpDeAgregar">
        Agregar <span class="glyphicon glyphicon-plus-sign"></span>
      </button>
    </div>
  </c:if>

  <div id="popUpDeAgregar" class="modal fade" role="dialog">
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
                  <option value="${marca.getId()}">${marca.getNombre()}</option>
                </c:forEach>
                <option value="0">Otra</option>
              </select>
              <div id="oculto" class="hidden">
                <br> <input name="nombreMarca" id="nombreMarca" class="form-control">
              </div>
            </div>
            <div class="form-group">
              <label>Modelo</label> <input name="modelo" id="modelo" class="form-control" type="text" required>
            </div>
            <fmt:formatDate var="year" value="<%=new java.util.Date()%>" pattern="yyyy"/>
            <c:set var="max" value="${year + 1}" />
            <c:set var="min" value="${year - 98}" />
            <div class="form-group">
              <label>A&ntilde;o</label> <input name="anno" id="anno" class="form-control" type="number" max="${max}" min="${min}" required>
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

  <div class="container">
    <hr>
    <footer>
      <p>&copy; Ren&aacute;n D&iacute;az Reyes 2016</p>
    </footer>
  </div>
  <script type="text/javascript">
  $("table").DataTable();
  
  var tipo = "${titulo}".toLowerCase();
  
  $("#agregar").click(function() {
    $.ajax({
      url: "${pageContext.request.contextPath}/vehiculos/" + tipo + "/agregar",
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
  
  $('.modal').on('hidden.bs.modal', function () {
    $(".modal input").val("");
    $(".modal select option:first").prop("selected", true);
  })
  
  $(".link").click(function() {
    var id = $(this).prop('id');
    window.location.href = "/Rutas/vehiculos/" + tipo + "/" + id;
  });
  </script>
</body>
</html>