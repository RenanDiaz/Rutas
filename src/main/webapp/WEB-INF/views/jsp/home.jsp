<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Rutas</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=0">
<spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
<spring:url value="/resources/core/css/common.css" var="commonCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${commonCss}" rel="stylesheet" />
<style type="text/css">
.badge {
  float: right;
}
</style>
</head>

<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="${pageContext.request.contextPath}/">Rutas</a>
    </div>
  </div>
</nav>
<br><br><br>

<div class="container">
  <div class="row">
    <div class="col-md-2">
      <ul class="nav nav-stacked">
        <li><a href="buses">Buses<span class="badge">${buses}</span></a></li>
        <li><a href="taxis">Taxis<span class="badge">${taxis}</span></a></li>
        <li><a href="particulares">Particulares<span class="badge">${particulares}</span></a></li>
        <li><a href="rutas">Rutas<span class="badge" id="rutas">${rutas}</span></a></li>
        <li><a href="ubicacion">Ubicaciones<span class="badge" id="ubicaciones">${ubicaciones}</span></a></li>
      </ul>
    </div>
  </div>
  <hr>
  <footer>
    <p>&copy; Ren&aacute;n D&iacute;az Reyes 2016</p>
  </footer>
</div>

<spring:url value="/resources/core/js/jquery-3.0.0.js" var="jquery" />
<spring:url value="/resources/core/js/common.js" var="coreJs" />
<spring:url value="/resources/core/js/bootstrap.min.js" var="bootstrapJs" />
<spring:url value="/resources/core/js/ubicacionAtmosphere.js" var="atmosphereJs" />

<script src="${jquery}"></script>
<script src="${coreJs}"></script>
<script src="${bootstrapJs}"></script>
<script src="${atmosphereJs}"></script>
</body>
</html>