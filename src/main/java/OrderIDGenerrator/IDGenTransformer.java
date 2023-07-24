package OrderIDGenerrator;

import OrderMessage.OrderMessage;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class IDGenTransformer implements Processor {
    int nextID=1;
    @Override
    public void process(Exchange exchange) throws Exception {
        OrderMessage om = exchange.getIn().getBody(OrderMessage.class);

        om.setOrderID(nextID);
        nextID++;
        exchange.getIn().setBody(om);
    }
}
