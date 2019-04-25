package bitcoin;

import bitcoin.rest.client.AlertRestClient;
import bitcoin.view.AlertsView;
import bitcoin.websocket.client.MyStompSessionHandler;
import bitcoin.websocket.client.RaisedAlertClient;
import org.springframework.boot.web.client.RestTemplateBuilder;

public class BitcoinAlertClient {

    public static void main(String[] args) {
        new BitcoinAlertClient().startApplication();
    }

    private  void startApplication(){
        AlertRestClient alertRestClient = new AlertRestClient(new RestTemplateBuilder().build());
        MyStompSessionHandler stompSessionHandler = new MyStompSessionHandler();
        RaisedAlertClient raisedAlertClient = new RaisedAlertClient(stompSessionHandler);

        AlertsView.createView(alertRestClient, stompSessionHandler, raisedAlertClient);
    }
}
