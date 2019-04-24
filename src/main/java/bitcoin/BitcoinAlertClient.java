package bitcoin;

import bitcoin.view.ConsoleUI;
import bitcoin.websocket.client.MyStompSessionHandler;
import bitcoin.websocket.client.RaisedAlertClient;

public class BitcoinAlertClient {
    public static void main(String[] args) {
        new RaisedAlertClient(new MyStompSessionHandler(new ConsoleUI())).connect();
        while(true);
    }
}
