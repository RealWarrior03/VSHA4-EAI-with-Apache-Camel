package BillingSystem;

import OrderMessage.OrderMessage;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import java.util.HashMap;

public class CustomerCreditStanding implements Processor{
    HashMap<Integer, Integer> customerHashMap = new HashMap<>();
    private static int priceOfSurfBoard = 250;
    private static int priceOfDivingSuit = 150;

    /*
    INPUT
        exchange
    OUTPUT
        none
     */
    @Override
    public void process(Exchange exchange) throws Exception {
        //TODO checken ob Customer genug cash hat; Problem ist: woher bekommen wir Infos über die Customer
        //disassembly of message
        OrderMessage message = exchange.getIn().getBody(OrderMessage.class);
        int customerID = message.getCustomerID();
        int nmbOrdDivingSuits = message.getNumberOfDivingSuits();
        int nmbOrdSurfBoards = message.getNumberOfSurfboards();
        boolean fromResSys = message.isResSysWasHere();

        int valueOfOrder = nmbOrdDivingSuits*priceOfDivingSuit+nmbOrdSurfBoards*priceOfSurfBoard;



        if (!message.isResSysWasHere()) {
            //add new customers to the system
            if (!customerHashMap.containsKey(customerID)) {
                customerHashMap.put(customerID, 500);
            }

            //check credit standing of customer
            if (valueOfOrder <= customerHashMap.get(customerID)) {
                customerHashMap.put(customerID, customerHashMap.get(customerID) - valueOfOrder);
                message.setValid(true);
                message.setValidationResult("customer has enough cash");
            } else {
                //substract valueOfOrder anyway for consistency reasons
                customerHashMap.put(customerID, customerHashMap.get(customerID) - valueOfOrder);
                message.setValidationResult("customer is too broke for this order");
            }
        } else {
            if (!message.isValid()) {
                customerHashMap.put(customerID, customerHashMap.get(customerID) + valueOfOrder);
            }
        }

    }

}
