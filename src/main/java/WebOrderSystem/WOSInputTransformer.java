package WebOrderSystem;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import OrderMessage.*;

public class WOSInputTransformer implements Processor {

    //TODO should we forward a String or a Message?
    /*
    INPUT
        exchange
    OUTPUT
        none
     */
    @Override
    public void process(Exchange exchange) throws Exception {
        String[] parts = exchange.getIn().getBody(String.class).split(", ");
        String firstName = parts[0];
        String lastName = parts[1];
        String nmbOrdSurfBoards = parts[2];
        String nmbOrdDivingSuits = parts[3];
        String customerID = parts[4];

        OrderMessage message = new OrderMessage(Integer.parseInt(customerID), firstName, lastName, Integer.parseInt(nmbOrdDivingSuits), Integer.parseInt(nmbOrdSurfBoards));

        exchange.getIn().setBody(message);

    }
}
