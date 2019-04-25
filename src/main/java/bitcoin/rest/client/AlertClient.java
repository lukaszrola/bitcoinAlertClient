package bitcoin.rest.client;

public interface AlertClient {
    void addAlert(String alertName, String limit, String currencyPair);

    void removeAlert(String alertName);
}
