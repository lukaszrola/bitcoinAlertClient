package integration;

import bitcoin.rest.client.AlertClient;
import bitcoin.rest.client.AlertRestClient;
import bitcoin.view.AlertsUI;
import bitcoin.websocket.client.MyStompSessionHandler;
import bitcoin.websocket.client.RaisedAlertClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class BitcoinAlertIntegrationTest {

    private static final String SOME_ALERT_NAME = "someAlert";
    private static final String SOME_LIMIT = "1";
    private static final String SOME_CURRENCY_PAIR = "BTC-USD";
    private static final int ACCEPTABLE_TIMEOUT = 15000;
    @Mock
    private AlertsUI alertsUI;
    private AlertClient alertClient;
    private MyStompSessionHandler stompSessionHandler;

    @BeforeEach
    void setUp() {
        connectAlertsUi();
        initRestClient();
    }

    private void initRestClient() {
        alertClient = new AlertRestClient(new RestTemplateBuilder().build());
    }

    private void connectAlertsUi() {
        stompSessionHandler = new MyStompSessionHandler();
        stompSessionHandler.setAlertsUI(alertsUI);
        new RaisedAlertClient(stompSessionHandler).connect();
    }

    @Test
    void sendAlertWithLowLimitAndReceiveNotification() {
        alertClient.addAlert(SOME_ALERT_NAME, SOME_LIMIT, SOME_CURRENCY_PAIR);

        Mockito.verify(alertsUI, Mockito.timeout(ACCEPTABLE_TIMEOUT).atLeastOnce()).printRaisedAlerts(Mockito.anyString());
    }
}
