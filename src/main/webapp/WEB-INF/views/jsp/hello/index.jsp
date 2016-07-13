<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Hello WebSocket</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=0">
<spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
<spring:url value="/resources/core/css/common.css" var="commonCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${commonCss}" rel="stylesheet" />

<spring:url value="/resources/core/stomp/sockjs-0.3.4.js" var="sockJs" />
<spring:url value="/resources/core/stomp/stomp.js" var="stompJs" />
<script src="${sockJs}"></script>
<script src="${stompJs}"></script>
<script type="text/javascript">
	var stompClient = null;

	function setConnected(connected) {
		document.getElementById('connect').disabled = connected;
		document.getElementById('disconnect').disabled = !connected;
		document.getElementById('conversationDiv').style.visibility = connected ? 'visible'
				: 'hidden';
		document.getElementById('response').innerHTML = '';
	}

	function connect() {
		var socket = new SockJS('/Rutas/hello');
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function(frame) {
			setConnected(true);
			console.log('Connected: ' + frame);
			stompClient.subscribe('/topic/rutas', function(ruta) {
				var message = JSON.parse(ruta.body);
				showMessage(message.id + " " + message.descripcion);
			});
			stompClient.subscribe('/topic/greetings', function(greeting) {
				var message = JSON.parse(greeting.body);
				showMessage(message.content);
			});
			stompClient.subscribe('/topic/contadores', function(greeting) {
				var message = greeting.body;
				showMessage(message);
			});
		});
	}

	function disconnect() {
		if (stompClient != null) {
			stompClient.disconnect();
		}
		setConnected(false);
		console.log("Disconnected");
	}

	function sendName() {
		var name = document.getElementById('name').value;
		stompClient.send("/app/hello", {}, JSON.stringify({
			'name' : name
		}));
	}

	function showMessage(message) {
		var response = document.getElementById('p');
        var p = document.createElement('p');
        p.style.wordWrap = 'break-word';
        p.appendChild(document.createTextNode(message));
        response.appendChild(p);
	}
</script>
</head>
<body onload="disconnect()">
  <noscript>
    <h2 style="color: #ff0000">Seems your browser doesn't support Javascript! Websocket relies on Javascript being enabled. Please enable Javascript and reload this page!</h2>
  </noscript>
  <div class="container">
    <div class="row">
      <div class="col-xs-12">
        <button id="connect" onclick="connect();" class="btn btn-info">Connect</button>
        <button id="disconnect" disabled="disabled" onclick="disconnect();" class="btn btn-info">Disconnect</button>
      </div>
    </div>
    <div class="row">
      <div class="col-xs-12 col-md-3">
        <div id="conversationDiv">
          <div class="form-group">
            <label>Name:</label><input type="text" id="name" class="form-control">
          </div>
          <button id="sendName" onclick="sendName();" class="btn btn-info">Send</button>
          <p id="response"></p>
        </div>
      </div>
    </div>
  </div>
  <spring:url value="/resources/core/js/jquery-3.0.0.js" var="jquery" />
  <spring:url value="/resources/core/js/common.js" var="coreJs" />
  <spring:url value="/resources/core/js/bootstrap.min.js" var="bootstrapJs" />
  <spring:url value="/resources/core/DataTables/datatables.min.js" var="datatablesJs" />

  <script src="${jquery}"></script>
  <script src="${coreJs}"></script>
  <script src="${bootstrapJs}"></script>
</body>
</html>