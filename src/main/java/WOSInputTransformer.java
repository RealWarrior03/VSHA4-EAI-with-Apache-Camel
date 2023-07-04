import org.apache.camel.Exchange;
import org.apache.camel.Processor;

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

        exchange.getIn().setBody(customerID + ", " + firstName + ", " + lastName + ", " + "null" + nmbOrdDivingSuits + ", "
                + nmbOrdSurfBoards + ", " + "null" + ", " + "null");

    }
}
