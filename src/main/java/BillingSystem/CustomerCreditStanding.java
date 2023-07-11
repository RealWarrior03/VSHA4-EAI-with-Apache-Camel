package BillingSystem;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import java.util.HashMap;

public class CustomerCreditStanding implements Processor{
    private static int priceOfSurfBoard = 420;
    private static int priceOfDivingSuit = 69;

    /*
    INPUT
        exchange
    OUTPUT
        none
     */
    @Override
    public void process(Exchange exchange) throws Exception {
        //TODO checken ob Customer genug cash hat; Problem ist: woher bekommen wir Infos über die Customer
        String[] parts = exchange.getIn().getBody(String.class).split(", ");
        String customerID = parts[0];
        String numberOfItems = parts[3];
        String nmbOrdDivingSuits = parts[4];
        String nmbOrdSurfBoards = parts[5];

        //if(Integer.parseInt(nmbOrdDivingSuits)*priceOfDivingSuit+Integer.parseInt(nmbOrdSurfBoards)*priceOfSurfBoard <= )
    }

}
