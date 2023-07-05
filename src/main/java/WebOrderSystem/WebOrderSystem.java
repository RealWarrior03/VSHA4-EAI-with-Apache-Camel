package WebOrderSystem;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class WebOrderSystem {
    private static final String SOURCE_FOLDER = "src/main/inputfiles/webordersysteminput.txt";
    private static final String DESTINATION_FOLDER = "src/main/outputfiles/webordersystemoutput.txt";


    /*
    INPUT:
    a file containing multiple orders seperated by \n
    order format:
    FirstName, LastName, Number of ordered surfboards, Number of ordered diving suits, Customer-ID

     OUTPUT:
     none
     */

    public static void main(String[] args) throws Exception {
        String filename = args[1];

        //activemq stuff
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        try {
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic("new_order");
            MessageProducer producer = session.createProducer(topic);
        } catch (Exception e) {
            e.printStackTrace();
        }

        CamelContext camelContext = new DefaultCamelContext();
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("file://" + SOURCE_FOLDER + "?noop=true")
                    .split(body().tokenize("\n"))
                    .process(new WOSInputTransformer()) //transformWOS
                    .process(new ContentEnricher())//enrich Message
                    .to("activemq:topic:new_oder");  //pubsub channel TODO might be incorrectly implemented
            }
        });
        camelContext.start();
        camelContext.stop();
    }
}

/*
o CustomerID
o FirstName
o LastName
o OverallItems (Number of all items in order)
o NumberOfDivingSuits
o NumberOfSurfboards o OrderID
o Valid
o validationResult
 */
