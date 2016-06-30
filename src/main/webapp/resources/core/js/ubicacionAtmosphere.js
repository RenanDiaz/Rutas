$(function() {
	"use strict";
	
	var badgeUbicaciones = $("#ubicaciones");
	var myName = false;
	var author = null;
	var logged = false;
	var socket = $.ubicacionAtmosphere;
	
	var request = { url: document.location.toString() + "/ubicaciones",
			contentType : "application/json",
	        logLevel : 'debug',
	        transport : 'websocket' ,
	        fallbackTransport: 'long-polling'};
	
	request.onOpen = function(response) {
		console.log('Atmosphere connected using ' + response.transport);
	};
	
	request.onTransportFailure = function(errorMsg, request) {
		jQuery.atmosphere.info(errorMsg);
		if(window.EventSource) {
			request.fallbacTransport = "sse";
		}
		console.log('Atmosphere Chat. Default transport is WebSocket, fallback is ' + request.fallbackTransport);
	};
	
	request.onReconnect = function(request, response) {
		socket.info("Reconnecting");
	};
	
	request.onMessage = function(response) {
		var message = response.responseBody;
		try {
			var json = jQuery.parseJSON(message);
		} catch(e) {
			console.log('This doesn\'t look like a valid JSON: ', message.data);
            return;
		}
		
		if (!logged) {
            logged = true;
            console.log(myName + ': ');
        } else {
            var me = json.prueba == 1;
            var date = typeof(json.time) == 'string' ? parseInt(json.time) : json.time;
            addMessage(json.prueba, me ? 'blue' : 'black', new Date(date));
        }
	};

    request.onClose = function(response) {
        logged = false;
    };
    
    var subSocket = socket.subscribe(request);

    function addMessage(author, message, color, datetime) {
    	badgeUbicaciones.html(author);
    	console.log(message);
    	console.log(color);
    	consolog.log(datetime);
    }
});