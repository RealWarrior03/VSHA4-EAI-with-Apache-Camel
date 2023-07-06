package BillingSystem;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class CustomerCreditStanding implements Processor{
    @Override
    public void process(Exchange exchange) throws Exception {
        //TODO checken ob Customer genug cash hat; Problem ist: woher bekommen wir Infos über die Customer
    }

}
