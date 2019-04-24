package bitcoin.websocket.client;

import bitcoin.view.AlertsUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.*;

public class MyStompSessionHandler extends StompSessionHandlerAdapter {
    private static final String RAISED_ALERTS_TOPIC = "/raisedAlerts/currentlyRaisedAlerts";
    private static final Logger logger = LoggerFactory.getLogger(MyStompSessionHandler.class);
    private final AlertsUI alertsUI;

    public MyStompSessionHandler(AlertsUI alertsUI) {
        this.alertsUI = alertsUI;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        session.subscribe(RAISED_ALERTS_TOPIC, this);
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

}
