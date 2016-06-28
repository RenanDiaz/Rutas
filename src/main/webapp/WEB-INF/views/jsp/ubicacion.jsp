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
<br><br><br>
<div class="container">
  <h2>
    <a href="/Rutas/ubicacion" class="btn btn-info">
      <span class="glyphicon glyphicon-menu-left"></span>
    </a>
    <c:if test="${not empty ubicacion.vehiculo()}">
      ${ubicacion.vehiculo().nombre()}
    </c:if>
    <c:if test="${empty ubicacion.vehiculo()}">
      No encontrado
    </c:if>
  </h2>
</div>
<br>
<c:if test="${not empty ubicacion}">
  <div class="container">
    <div class="table-responsive">
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
            <td>${ubicacion.ruta()}</td>
            <td>${ubicacion.latitud()}</td>
            <td>${ubicacion.longitud()}</td>
          </tr>
        </tbody>
      </table>
    </div>
  <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#popUpDeConfirmacion">Editar</button>
  </div>
</c:if>
<div id="popUpDeConfirmacion" class="modal fade" role="dialog">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Editar ubicaci&oacute;n</h4>
      </div>
      <div class="modal-body" id="mensaje">
        <c:if test="${not empty vehiculos}">
          <div class="form-group">
            <label>Veh&iacute;culo</label>
            <select name="placa" id="placa" class="form-control">
              <c:forEach items="${vehiculos}" var="vehiculo">
                <c:if test="${vehiculo.placa() == ubicacion.vehiculo().placa()}">
                  <option value="${vehiculo.placa()}" selected>${vehiculo.nombre()}</option>
                </c:if>
                <c:if test="${vehiculo.placa() != ubicacion.vehiculo().placa()}">
                  <option value="${vehiculo.placa()}">${vehiculo.nombre()}</option>
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
</body>
</html>