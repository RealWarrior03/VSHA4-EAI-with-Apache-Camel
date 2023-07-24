package PrintDebug;

import OrderMessage.OrderMessage;
import org.apache.camel.Exchange;

import org.apache.camel.Processor;

public class PrintDebug implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
         OrderMessage om = exchange.getIn().getBody(OrderMessage.class);
         System.out.println(om.toString());
    }
}
