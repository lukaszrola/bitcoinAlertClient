package bitcoin;

import bitcoin.rest.client.AlertRestClient;
import bitcoin.view.AlertsView;
import bitcoin.websocket.client.RaisedAlertsHandler;
import bitcoin.websocket.client.RaisedAlertListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;

@SpringBootApplication
public class BitcoinAlertClient extends JFrame implements CommandLineRunner {
    private final AlertRestClient alertRestClient;
    private final RaisedAlertsHandler raisedAlertsHandler;
    private final RaisedAlertListener raisedAlertListener;

    @Autowired
    public BitcoinAlertClient(AlertRestClient alertRestClient, RaisedAlertsHandler raisedAlertsHandler, RaisedAlertListener raisedAlertListener) {
        this.alertRestClient = alertRestClient;
        this.raisedAlertsHandler = raisedAlertsHandler;
        this.raisedAlertListener = raisedAlertListener;
    }

    public static void main(String[] args) {
        SpringApplication.run(BitcoinAlertClient.class, args);
    }

    @Override
    public void run(String... args) {
        this.startApplication();

    }

    private void startApplication() {
        AlertsView.createView(alertRestClient, raisedAlertsHandler, raisedAlertListener);
    }
}
