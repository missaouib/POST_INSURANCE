var app={initialize:function(){this.bindEvents()},bindEvents:function(){document.addEventListener("deviceready",this.onDeviceReady,false)},onDeviceReady:function(){app.receivedEvent("deviceready")},receivedEvent:function(G){var H=document.getElementById(G);var E=H.querySelector(".listening");var F=H.querySelector(".received");E.setAttribute("style","display:none;");F.setAttribute("style","display:block;");console.log("Received Event: "+G)}};