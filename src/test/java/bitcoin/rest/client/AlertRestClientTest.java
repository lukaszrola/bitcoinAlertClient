package bitcoin.rest.client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

import static bitcoin.rest.client.AlertRestClient.ADD_ALERT_URL;
import static bitcoin.rest.client.AlertRestClient.DELETE_ALERT_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class AlertRestClientTest {
    private static final String SOME_ALERT_NAME = "someAlert";
    private static final String SOME_LIMIT = BigDecimal.ONE.toString();
    private static final String SOME_CURRENCY_PAIR = "BTC-USD";
    private static final String NAME_KEY = "name";
    private static final String LIMIT_KEY = "limit";
    private static final String PAIR_KEY = "pair";

    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private AlertRestClient alertRestClient;

    @Test
    void shouldSendPutAlert() {
        alertRestClient.addAlert(SOME_ALERT_NAME, SOME_LIMIT, SOME_CURRENCY_PAIR);

        ArgumentCaptor<Map<String, String>> params = ArgumentCaptor.forClass(Map.class);
        verify(restTemplate).put(eq(ADD_ALERT_URL), Mockito.any(), params.capture());

        Map<String, String> requestParameters = params.getValue();
        assertThat(requestParameters).containsOnlyKeys(NAME_KEY, LIMIT_KEY, PAIR_KEY);
        assertThat(requestParameters.values()).containsOnly(SOME_ALERT_NAME, SOME_LIMIT, SOME_CURRENCY_PAIR);
    }

    @Test
    void shouldSendDeleteAlert() {
        alertRestClient.removeAlert(SOME_ALERT_NAME);

        ArgumentCaptor<Map<String, String>> params = ArgumentCaptor.forClass(Map.class);
        verify(restTemplate).delete(eq(DELETE_ALERT_URL), params.capture());

        assertThat(params.getValue()).containsOnly(Map.entry(NAME_KEY, SOME_ALERT_NAME));
    }
}