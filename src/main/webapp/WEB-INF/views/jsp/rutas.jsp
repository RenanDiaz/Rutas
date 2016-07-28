<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
<title>Rutas - Rutas</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=0">

<spring:url value="/resources/core/js/jquery-3.0.0.js" var="jquery" />
<spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
<spring:url value="/resources/core/js/bootstrap.min.js" var="bootstrapJs" />
<spring:url value="/resources/core/css/common.css" var="commonCss" />
<spring:url value="/resources/core/js/common.js" var="commonJs" />
<spring:url value="/resources/core/DataTables/datatables.min.css" var="datatablesCss" />
<spring:url value="/resources/core/DataTables/datatables.min.js" var="datatablesJs" />
<spring:url value="/resources/core/stomp/sockjs-0.3.4.js" var="sockJs" />
<spring:url value="/resources/core/stomp/stomp.js" var="stompJs" />

<script src="${jquery}"></script>
<link href="${bootstrapCss}" rel="stylesheet" />
<script src="${bootstrapJs}"></script>
<link href="${commonCss}" rel="stylesheet" />
<script src="${commonJs}"></script>
<link href="${datatablesCss}" rel="stylesheet" />
<script src="${datatablesJs}"></script>
<script src="${sockJs}"></script>
<script src="${stompJs}"></script>

<script type="text/javascript">
  var datatable = null;
  var stompClient = null;

  function connect() {
	  var socket = new SockJS('/Rutas/hello');
    stompClient = Stomp.over(socket);
//     stompClient.debug = null;
    stompClient.connect({}, function(frame) {
//       console.log('Connected: ' + frame);
      stompClient.subscribe('/topic/rutas', function(ruta) {
        var message = JSON.parse(ruta.body);
        updateValues(message);
      });
    });
  }

  function disconnect() {
    if (stompClient != null) {
      stompClient.disconnect();
    }
//     console.log("Disconnected");
  }

  function send() {
    stompClient.send("/app/rutas/agregar", {}, JSON.stringify({
      "origen": $("#origen").val(),
      "destino": $("#destino").val()
    }));
  }

  function updateValues(message) {
    var row = $("<tr />", { class: "link", id: message.id });
    row.append("<td>" + message.id + "</td>");
    row.append("<td>" + message.origen + "</td>");
    row.append("<td>" + message.destino + "</td>");
    datatable.row.add(row).draw();
  }
  
  function setQuantity(id, object) {
  var span = $("#" + id);
  span.html(object);
  }
</script>

</head>
<body onload="connect()">

<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="${pageContext.request.contextPath}/">Rutas</a>
    </div>
  </div>
</nav>
<br><br><br>
<div class="container">
  <h2>
    <a href="${pageContext.request.contextPath}/" class="btn btn-info">
      <span class="glyphicon glyphicon-triangle-left"></span>
    </a>
    Rutas
  </h2>
</div>
<br>

<c:if test="${not empty rutas}">
  <div class="container">
    <div class="table-responsive">
      <table class="table table-striped table-hover">
        <thead>
          <tr>
            <th>ID</th>
            <th>Origen</th>
            <th>Destino</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${rutas}" var="ruta">
            <tr class="link" id="${ruta.getId()}">
              <td class="text-center">${ruta.getId()}</td>
              <td>${ruta.getOrigen()}</td>
              <td>${ruta.getDestino()}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#popUpDeAgregar">Agregar <span class="glyphicon glyphicon-plus-sign"></span></button>
  </div>
</c:if>

<div id="popUpDeAgregar" class="modal fade" role="dialog">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Agregar ruta</h4>
      </div>
      <div class="modal-body">
        <div class="form-group">
          <label>Origen</label> <input id="origen" class="form-control" type="text" required autofocus>
        </div>
        <div class="form-group">
          <label>Destino</label> <input id="destino" class="form-control" type="text" required>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal" id="agregar" onclick="send()">Agregar</button>
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
datatable = $("table").DataTable();

// $("#agregar").click(function() {
//   $.ajax({
//     url: "${pageContext.request.contextPath}/rutas/agregar",
//     method: "POST",
//     data: {
//       origen: $("#origen").val(),
//       destino: $("#destino").val()
//     },
//     success: function(data) {
//       var row = $("<tr />", { class: "link", id: data.id });
//       row.append("<td>" + data.id + "</td>");
//       row.append("<td>" + data.origen + "</td>");
//       row.append("<td>" + data.destino + "</td>");
//       datatable.row.add(row).draw();
//     }
//   });
// });

$(document).on("click", ".link", function() {
  var id = $(this).prop('id');
  window.location.href = "${pageContext.request.contextPath}/rutas/" + id;
});
</script>
</body>
</html>