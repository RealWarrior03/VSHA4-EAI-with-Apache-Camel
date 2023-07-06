package CallCenterOrderSystem;

import ContentEnricher.*;
import OrderMessage.OrderMessage;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class CallCenterOrderSystem {
    private static final String SOURCE_FOLDER = "src/main/inputfiles";
    private static final String DESTINATION_FOLDER = "src/main/outputfiles";

    /*
    PARAMS:
    foldername filename
    example: src/main/inputfiles callcenterordersysteminput.txt

    INPUT:
    a file containing multiple orders seperated by \n
    order format:
    FirstName, LastName, Number of ordered surfboards, Number of ordered diving suits, Customer-ID

     OUTPUT:
     none
     */

    public static void main(String[] args) throws Exception {
        String foldername = args[0];
        String filename = args[1];

        //activemq stuff
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        connectionFactory.setTrustAllPackages(true);
        try {
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //Topic topic = session.createTopic("new_order");
            //MessageProducer producer = session.createProducer(topic);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread.sleep(10000);

        CamelContext camelContext = new DefaultCamelContext();
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("file:" + foldername + "?fileName=" + filename + "&noop=true")
                        .split(body().tokenize("\n"))
                        .process(new WOSInputTransformer()) //transformWOS
                        .process(new ContentEnricher())//enrich Message

                        //printing out MessageObjects
                        .process(exchange -> {
                            // Hole den Inhalt der Datei
                            OrderMessage content = exchange.getIn().getBody(OrderMessage.class);

                            // Gib den Inhalt in der Konsole aus
                            System.out.println("Content of the OrderMessage Object");
                            System.out.println(content.toString());
                        })

                        .to("activemq:queue:orderIDGenIn");  //to queue channel TODO might be incorrectly implemented
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