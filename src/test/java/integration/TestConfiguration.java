package integration;

import bitcoin.rest.client.AlertClient;
import bitcoin.rest.client.AlertRestClient;
import bitcoin.websocket.client.RaisedAlertsHandler;
import bitcoin.websocket.client.RaisedAlertListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

@Configuration
@EnableAutoConfiguration
@PropertySource("classpath:application.properties")
class TestConfiguration {

    @Bean
    RaisedAlertListener raisedAlertClient(StompSessionHandler stompSessionHandler, @Value("${websocket.raised.alert.endpoint}") String raisedAlertsWebsocketEndpoint) {
        return new RaisedAlertListener(stompSessionHandler, raisedAlertsWebsocketEndpoint);
    }

    @Bean
    RaisedAlertsHandler raisedAlertsHandler(@Value("${websocket.raised.alert.topic}") String raisedAlertsTopic) {
        return new RaisedAlertsHandler(raisedAlertsTopic);
    }

    @Bean
    AlertClient alertClient(@Value("${rest.alert.host.adres}") String hostUrl) {
        return new AlertRestClient(new RestTemplateBuilder().build(), hostUrl);
    }
}
