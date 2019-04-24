package bitcoin.websocket.client;

import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;

public class RaisedAlertClient {
    private static final String RAISED_ALERTS_WEBSOCKET_ENDPOINT = "ws://localhost:8080/alertsSender";
    private final StompSessionHandler stompSessionHandler;

    public RaisedAlertClient(StompSessionHandler stompSessionHandler) {
        this.stompSessionHandler = stompSessionHandler;
    }

    public void connect(){
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport( new StandardWebSocketClient()) );
        WebSocketClient webSocketClient = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);

        stompClient.setMessageConverter(new StringMessageConverter());

        stompClient.connect(RAISED_ALERTS_WEBSOCKET_ENDPOINT, stompSessionHandler);}
}
