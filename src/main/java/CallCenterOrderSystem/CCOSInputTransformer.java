package CallCenterOrderSystem;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import OrderMessage.*;

public class CCOSInputTransformer implements Processor {

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
        String customerID = parts[0];
        String[] fullName = parts[1].split(" ");
        String firstName = fullName[0];
        String lastName = fullName[1];
        String nmbOrdSurfBoards = parts[2];
        String nmbOrdDivingSuits = parts[3];

        OrderMessage message = new OrderMessage(Integer.parseInt(customerID), firstName, lastName, Integer.parseInt(nmbOrdDivingSuits), Integer.parseInt(nmbOrdSurfBoards));

        exchange.getIn().setBody(message);

    }
}
