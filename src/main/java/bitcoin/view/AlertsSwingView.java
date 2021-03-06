package bitcoin.view;

import bitcoin.rest.client.AlertClient;
import bitcoin.rest.client.AlertRestClient;
import bitcoin.websocket.client.RaisedAlertsHandler;
import bitcoin.websocket.client.RaisedAlertListener;

import javax.swing.*;
import java.awt.*;

public class AlertsSwingView extends JFrame implements AlertsUI {
    private final AlertClient alertRestClient;
    private final TextArea raisedAlerts = new TextArea("", 30, 90);
    private final TextField alertName = new TextField(20);
    private final TextField limit = new TextField(15);
    private final TextField currencyPair = new TextField(8);

    private AlertsSwingView(AlertClient alertRestClient) {
        this.setTitle("Bitcoin Alerts");
        this.alertRestClient = alertRestClient;
        JPanel panel = new JPanel();
        panel.add(new Label("Alert name:"));
        panel.add(alertName);
        panel.add(new Label("Limit:"));
        panel.add(limit);
        panel.add(new Label("Currency pair:"));
        panel.add(currencyPair);
        panel.add(addAlertButton());
        panel.add(addDeleteButton());
        panel.add(raisedAlerts);
        add(panel);
        setSize(600, 600);
    }

    public static void createView(AlertClient alertRestClient, RaisedAlertsHandler raisedAlertsHandler, RaisedAlertListener raisedAlertListener) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            AlertsSwingView alertsSwingView = new AlertsSwingView(alertRestClient);
            raisedAlertsHandler.setAlertsUI(alertsSwingView);
            raisedAlertListener.connect();
            alertsSwingView.setVisible(true);
        });
    }

    private JButton addDeleteButton() {
        JButton delete_alert = new JButton("Delete alert");
        delete_alert.addActionListener(listener -> alertRestClient.removeAlert(alertName.getText()));
        return delete_alert;
    }

    private JButton addAlertButton() {
        JButton add_alert = new JButton("Add alert");
        add_alert.addActionListener( l -> alertRestClient.addAlert(alertName.getText(),limit.getText(),currencyPair.getText()));
        return add_alert;
    }

    @Override
    public void printRaisedAlerts(String raisedAlerts) {
        this.raisedAlerts.setText(raisedAlerts);
    }
}
