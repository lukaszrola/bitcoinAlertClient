package bitcoin.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleUI implements AlertsUI {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleUI.class);

    @Override
    public void printRaisedAlerts(String raisedAlerts) {
        logger.info(raisedAlerts);
    }
}
