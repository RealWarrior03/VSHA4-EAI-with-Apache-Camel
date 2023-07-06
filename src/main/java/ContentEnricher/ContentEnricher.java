package ContentEnricher;

import OrderMessage.OrderMessage;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.core.annotation.Order;

public class ContentEnricher implements Processor {
    /*
    INPUT
        exchange
    OUTPUT
        none
    FUNC:
        adds overallItems, calculated using numberOfSurfBoards and numberOfDivingSuits
     */
    @Override
    public void process(Exchange exchange) throws Exception {
        OrderMessage message = exchange.getIn().getBody(OrderMessage.class);
        message.setOverallItems(message.getNumberOfSurfboards() + message.getNumberOfDivingSuits());

        exchange.getOut().setBody(message);
    }
}
