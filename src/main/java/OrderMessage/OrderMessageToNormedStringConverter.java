package OrderMessage;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class OrderMessageToNormedStringConverter implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String[] normedString = exchange.getIn().getBody(OrderMessage.class).getAllValuesAsStringArray();
        exchange.getIn().setBody(normedString);
    }
}