package bitcoin.websocket.client;

import bitcoin.view.AlertsUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;

@Component
public class RaisedAlertsHandler extends StompSessionHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(RaisedAlertsHandler.class);
    private static final String WEBSOCKET_TOPIC = "${websocket.raised.alert.topic}";
    private AlertsUI alertsUI;
    private final String raisedAlertsTopic;

    public RaisedAlertsHandler(@Value(WEBSOCKET_TOPIC) String raisedAlertsTopic) {
        this.raisedAlertsTopic = raisedAlertsTopic;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        session.subscribe(raisedAlertsTopic, this);
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        super.handleException(session, command, headers, payload, exception);
        exception.printStackTrace();
        logger.warn("Exception occured");
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        super.handleTransportError(session, exception);
        logger.warn("Warning occured");
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        alertsUI.printRaisedAlerts(payload.toString());
    }

    public void setAlertsUI(AlertsUI alertsUI) {
        this.alertsUI = alertsUI;
    }
}
