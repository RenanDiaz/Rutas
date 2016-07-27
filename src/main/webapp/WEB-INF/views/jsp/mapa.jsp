<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <c:if test="${empty asignacion}">
      <title>Rutas - Mapa de ${vehiculo.getNombreCorto()}</title>
    </c:if>
    <c:if test="${not empty asignacion}">
      <title>Rutas - Mapa de ${asignacion.getDescripcion()}</title>
    </c:if>
    <style>
      html, body {
        height: 100%;
        margin: 0;
        padding: 0;
      }
      #map {
        height: 100%;
      }
    </style>
  </head>
  <body>
    <div id="map"></div>
    <spring:url value="/resources/core/js/jquery-3.0.0.js" var="jquery" />
    <script src="${jquery}"></script>
    <script>

    var coordenadas = [];
    var latTotal = 0;
    var lngTotal = 0;
    var cantTotal = 0;
    var vehiculo;

    function initMap() {
      var url;
      if("${empty asignacion}" == "true") {
      	url = "${pageContext.request.contextPath}/rest/ubicaciones?placa=${vehiculo.getPlaca()}&inicio=${inicio}&fin=${fin}";
      } else {
    	url = "${pageContext.request.contextPath}/rest/recorrido?asignacion=${asignacion.getId()}";
      }
      $.ajax({
          url: url,
          method: "GET",
          success: function(data) {
          if(data.hasOwnProperty("ubicaciones")) {
            vehiculo = data.ubicaciones[0].asignacion.vehiculo;
            $.each(data.ubicaciones, function(key, value) {
              var lat = Number(value.latitud);
              var lng = Number(value.longitud);
              latTotal += lat;
              lngTotal += lng;
              cantTotal++;
              var ubicacion = {"lat": lat, "lng": lng};
              coordenadas.push(ubicacion);
            });
          } else {
            vehiculo = data.vehiculo;
            var lat = Number(data.latitud);
            var lng = Number(data.longitud);
            latTotal = lat;
            lngTotal = lng;
            cantTotal = 1;
            var ubicacion = {"lat": lat, "lng": lng};
            coordenadas.push(ubicacion);
          }
          
          var latCenter = latTotal / cantTotal;
          var lngCenter = lngTotal / cantTotal;
          var center = {"lat": latCenter, "lng": lngCenter};
          var map = new google.maps.Map(document.getElementById('map'), {
            zoom: 15,
            center: center,
            mapTypeId: google.maps.MapTypeId.ROADMAP
          });
          var route = new google.maps.Polyline({
            path: coordenadas,
            geodesic: true,
            strokeColor: '#FF0000',
            strokeOpacity: 0.5,
            strokeWeight: 3
          });
          route.setMap(map);

          if(coordenadas.length == 1)
          {
            var marker = new google.maps.Marker({
              position: coordenadas[0],
              map: map,
              title: vehiculo.nombreCorto
            });
          } else {
      	    var marker1 = new google.maps.Marker({
              position: coordenadas[0],
              map: map,
              title: "Inicio"
            });
      	  
      	    var marker2 = new google.maps.Marker({
              position: coordenadas[cantTotal - 1],
              map: map,
              title: "Fin"
            });
          }
        }
      });
    }
    </script>
    <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCCZK_NqEkdDLgJz0OyWL-FvrxkZn1EbOQ&callback=initMap">
    </script>
  </body>
</html>