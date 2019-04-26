package bitcoin.rest.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class AlertRestClient implements AlertClient {

    private static final String HOST_URL = "${rest.alert.host.adres}";
    private static final String ADD_REQUEST = "/alert?name={name}&limit={limit}&pair={pair}";
    private static final String DELETE_REQUEST = "/alert?name={name}";

    private final RestTemplate restTemplate;
    private final String addAlertUrl;
    private final String deleteAlertUrl;

    public AlertRestClient(RestTemplate restTemplate, @Value(HOST_URL) String hostUrl) {
        this.restTemplate = restTemplate;
        addAlertUrl = hostUrl + ADD_REQUEST;
        deleteAlertUrl = hostUrl + DELETE_REQUEST;
    }

    @Override
    public void addAlert(String alertName, String limit, String currencyPair) {
        restTemplate.put(addAlertUrl, httpEntity(), addAlertParams(alertName, limit, currencyPair));
    }

    @Override
    public void removeAlert(String alertName) {
        restTemplate.delete(deleteAlertUrl, addDeleteParam(alertName));
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
