<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
  <head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <title>Simple Polylines</title>
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
    
    $.ajax({
  	  url: "${pageContext.request.contextPath}/rest/ubicaciones?placa=${vehiculo.getPlaca()}",
      method: "GET",
      success: function(data) {
        $.each(data.ubicaciones, function(key, value) {
          var ubicacion = {"lat":Number(value.latitud), "lng":Number(value.longitud)};
          coordenadas.push(ubicacion);
        });
      }
    });

    function initMap() {
      var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 3,
        center: {lat: 0, lng: -180},
        mapTypeId: google.maps.MapTypeId.TERRAIN
      });
      var flightPath = new google.maps.Polyline({
        path: coordenadas,
        geodesic: true,
        strokeColor: '#FF0000',
        strokeOpacity: 1.0,
        strokeWeight: 2
      });

      flightPath.setMap(map);
    }
    </script>
    <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCCZK_NqEkdDLgJz0OyWL-FvrxkZn1EbOQ&callback=initMap">
    </script>
  </body>
</html>