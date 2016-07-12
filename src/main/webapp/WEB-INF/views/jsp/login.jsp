<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Rutas</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=0">
<style type="text/css">
.badge {
  float: right;
}
</style>
</head>
<body>
<div class="container">
  <div class="row">
    <div class="col-md-2">
      <form action="/Rutas/login" method="post">
        <label for="username">Usuario</label>
        <br>
        <input name="username" type="email" required>
        <br><br>
        <label for="password">Contrase&ntilde;a</label>
        <br>
        <input name="password" type="password" required>
        <br><br>
        <input type="submit" value="Entrar">
      </form>
    </div>
  </div>
  <hr>
  <footer>
    <p>&copy; Ren&aacute;n D&iacute;az Reyes 2016</p>
  </footer>
</div>
</body>
</html>