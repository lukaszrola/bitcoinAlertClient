package integration;

import bitcoin.rest.client.AlertClient;
import bitcoin.view.AlertsUI;
import bitcoin.websocket.client.RaisedAlertsHandler;
import bitcoin.websocket.client.RaisedAlertsListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@RunWith(JUnitPlatform.class)
@ContextConfiguration(classes = TestConfiguration.class)
class BitcoinAlertIntegrationTest {

    private static final String SOME_ALERT_NAME = "someAlert";
    private static final String SOME_LIMIT = "1";
    private static final String SOME_CURRENCY_PAIR = "BTC-USD";
    private static final int ACCEPTABLE_TIMEOUT = 15000;

    @Mock
    private AlertsUI alertsUI;
    @Autowired
    private AlertClient alertClient;
    @Autowired
    private RaisedAlertsHandler stompSessionHandler;
    @Autowired
    private RaisedAlertsListener raisedAlertsListener;

    @BeforeEach
    void setUp() {
        connectAlertsUi();
    }

    private void connectAlertsUi() {
        stompSessionHandler.setAlertsUI(alertsUI);
        raisedAlertsListener.connect();
    }

    @Test
    void sendAlertWithLowLimitAndReceiveNotification() {
        alertClient.addAlert(SOME_ALERT_NAME, SOME_LIMIT, SOME_CURRENCY_PAIR);

        Mockito.verify(alertsUI, Mockito.timeout(ACCEPTABLE_TIMEOUT).atLeastOnce()).printRaisedAlerts(Mockito.anyString());
    }
}
