package bitcoin;

import bitcoin.rest.client.AlertRestClient;
import bitcoin.view.AlertsView;
import bitcoin.websocket.client.MyStompSessionHandler;
import bitcoin.websocket.client.RaisedAlertClient;
import org.springframework.boot.web.client.RestTemplateBuilder;

import javax.swing.*;

public class BitcoinAlertClient {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            AlertsView alertsView = new AlertsView(new AlertRestClient(new RestTemplateBuilder().build()));
            new RaisedAlertClient(new MyStompSessionHandler(alertsView)).connect();
            alertsView.setVisible(true);
        });
    }
}
