package WebOrderSystem;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class WebOrderSystem {
    private static final String SOURCE_FOLDER = "src/main/inputfiles";
    private static final String DESTINATION_FOLDER = "src/main/outputfiles";

    /*
    INPUT:
    a file containing multiple orders seperated by \n
    order format:
    FirstName, LastName, Number of ordered surfboards, Number of ordered diving suits, Customer-ID

     OUTPUT:
     none
     */

    public static void main(String[] args) throws Exception {
        String filename = args[0];

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

        Thread.sleep(10000);

        CamelContext camelContext = new DefaultCamelContext();
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("file:" + SOURCE_FOLDER + "?fileName=webordersysteminput.txt&noop=true")

                    .process(exchange -> {
                        // Hole den Inhalt der Datei
                        String content = exchange.getIn().getBody(String.class);

                        // Gib den Inhalt in der Konsole aus
                        System.out.println("Inhalt der Datei: " + content);
                    })

                    .split(body().tokenize("\n"))
                    .process(new WOSInputTransformer()) //transformWOS
                    .process(new ContentEnricher())//enrich Message
                    .to("activemq:topic:new_oder");  //pubsub channel TODO might be incorrectly implemented
                    //.transform(body().append("\n"))
                    //.to("file:" + DESTINATION_FOLDER + "?fileName=webordersystemoutput.txt&noop=true&fileExist=Append"); //only for debugging
            }
        });
        camelContext.start();

        //camelContext.createProducerTemplate().sendBody("direct:start", "Peter, Parker, 2, 0, 1");

        Thread.sleep(5000);

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
