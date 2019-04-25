package bitcoin.rest.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class AlertRestClient implements AlertClient {
    static final String ADD_ALERT_URL = "http://localhost:8080/alert?name={name}&limit={limit}&pair={pair}";
    static final String DELETE_ALERT_URL = "http://localhost:8080/alert?name={name}";

    private final RestTemplate restTemplate;

    public AlertRestClient() {
        this.restTemplate = new RestTemplateBuilder().build();
    }

    @Override
    public void addAlert(String alertName, String limit, String currencyPair) {
        restTemplate.put(ADD_ALERT_URL, httpEntity(), addAlertParams(alertName, limit, currencyPair));
    }

    @Override
    public void removeAlert(String alertName) {
        restTemplate.delete(DELETE_ALERT_URL, addDeleteParam(alertName));
    }

    private Map<String, String> addDeleteParam(String alertName) {
        return Map.of("name",alertName);
    }

    private HttpEntity httpEntity() {
        return new HttpEntity(httpHeader());
    }

    private HttpHeaders httpHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        return headers;
    }

    private Map<String, String> addAlertParams(String alertName, String limit, String currencyPair) {
        return Map.of("name", alertName,
                "limit", limit,
                "pair", currencyPair);
    }
}
