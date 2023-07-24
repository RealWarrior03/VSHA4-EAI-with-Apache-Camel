package OrderMessage;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class NormedStringToOrderMessageConverter implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        OrderMessage message = new OrderMessage(exchange.getIn().getBody(String[].class));
        exchange.getIn().setBody(message);
    }
}
