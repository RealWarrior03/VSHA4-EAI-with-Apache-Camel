package WebOrderSystem;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class ContentEnricher implements Processor {
    /*
    INPUT
        exchange
    OUTPUT
        none
     */
    @Override
    public void process(Exchange exchange) throws Exception {
        String[] parts = exchange.getIn().getBody(String.class).split(", ");
        Integer val1 = Integer.parseInt(parts[4]);
        Integer val2 = Integer.parseInt(parts[5]);
        parts[3] = Integer.toString(val1 + val2);

        exchange.getOut().setBody(String.join(", ", parts));
    }
}
