let stompClient = null;

function connect() {
    const serverIp = document.getElementById('serverIp').value;
    
//conexion y subcripcion
    const socket = new SockJs(`http://${serverIp}:8080/chat`)

    stompClient = Stomp.over(socket);
    stompClient.connect({}, frame => {
        setConnected(true);
        stompClient.subscribe("topic/public", message => {
            showMessage(JSON.parse(message.body));
        }, error => {
            console.error(error);
            setConnected(false);
        });
    })
}

function disconnect() {
    // Desconectar
    setConnected(false);
    console.log("Disconnected");
}

function setConnected(connected) {
    const status = document.getElementById('status');
    if (connected) {
        status.className = 'connected';
        status.textContent = 'Conectado';
    } else {
        status.className = 'disconnected';
        status.textContent = 'Desconectado';
    }
    document.getElementById('message').disabled = !connected;
}

function sendMessage() {
    const username = document.getElementById('username').value;
    const content = document.getElementById('message').value;
    
    if (content.trim() === '') return;
    
     // Conexion
     stompClient.send("/app/chat.sendMessage", {}, JSON.stringify({ 
        tipo: 'CHAT', 
        usuario: username, 
        contenido: content 
    }));
    
    document.getElementById('message').value = '';
}

function showMessage(message) {
    const messages = document.getElementById('messages');
    const messageDiv = document.createElement('div');
    messageDiv.className = 'message';
    messageDiv.innerHTML = '<strong>' + message.usuario + ':</strong> ' + message.contenido;
    messages.appendChild(messageDiv);
    messages.scrollTop = messages.scrollHeight;
}

// Allow sending message with Enter key
document.getElementById('message').addEventListener('keypress', function (e) {
    if (e.key === 'Enter') {
        sendMessage();
    }
});
