package bitcoin.websocket.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;

@Component
public class RaisedAlertClient {
    private final StompSessionHandler stompSessionHandler;
    private final String raisedAlertsWebsocketEndpoint;

    public RaisedAlertClient(StompSessionHandler stompSessionHandler, @Value("${websocket.raised.alert.endpoint}") String raisedAlertsWebsocketEndpoint) {
        this.stompSessionHandler = stompSessionHandler;
        this.raisedAlertsWebsocketEndpoint = raisedAlertsWebsocketEndpoint;
    }

    public void connect(){
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport( new StandardWebSocketClient()) );
        WebSocketClient webSocketClient = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);

        stompClient.setMessageConverter(new StringMessageConverter());

        stompClient.connect(raisedAlertsWebsocketEndpoint, stompSessionHandler);}
}
