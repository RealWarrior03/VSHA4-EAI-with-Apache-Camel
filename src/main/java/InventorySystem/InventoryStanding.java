package InventorySystem;

import OrderMessage.OrderMessage;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class InventoryStanding implements Processor {
    private int availableSurfBoards = 100;
    private int availableDivingSuits = 100;

    @Override
    public void process(Exchange exchange) throws Exception {
        //disassembly of message
        OrderMessage message = exchange.getIn().getBody(OrderMessage.class);
        int nmbOrdDivingSuits = message.getNumberOfDivingSuits();
        int nmbOrdSurfBoards = message.getNumberOfSurfboards();
        boolean fromResSys = message.isResSysWasHere();

        if (!message.isResSysWasHere()) {
            //update inventory data
            availableDivingSuits -= nmbOrdDivingSuits;
            availableSurfBoards -= nmbOrdSurfBoards;

            if(availableSurfBoards < 0){
                message.setValid(false);
                message.setValidationResult("not enough surfboard in the inventory");
            }else if(availableDivingSuits < 0){
                message.setValid(false);
                message.setValidationResult("not enough divingsuits in the inventory");
            }else if(availableDivingSuits < 0 && availableSurfBoards < 0){
                message.setValid(false);
                message.setValidationResult("not enough divingsuits and surfboards in the inventory");
            }else{
                message.setValid(true);
                message.setValidationResult("products are in the inventory");
            }
        }
        else { //handle response from ResultSystem
            if (!message.isValid()) {
                availableDivingSuits += nmbOrdDivingSuits;
                availableSurfBoards += nmbOrdSurfBoards;
            }
        }
    }
}
