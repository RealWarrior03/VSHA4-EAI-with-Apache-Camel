package WebOrderSystem;

import Client.Client;
import ContentEnricher.*;
import OrderMessage.*;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.activemq.ActiveMQConnectionFactory;
import PrintDebug.PrintDebug;

import javax.jms.*;

public class WebOrderSystem {
    private static final String SOURCE_FOLDER = "src/main/inputfiles";
    private static final String DESTINATION_FOLDER = "src/main/outputfiles";


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
            Queue queue = session.createQueue("orderIDGenIn");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread.sleep(10000);
        CamelContext camelContext = new DefaultCamelContext();
            camelContext.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                from("direct:start")
                        .process(new WOSInputTransformer()) //transformWOS
                        .process(new ContentEnricher())//enrich Message
                        .process(new OrderMessageToNormedStringConverter())
                        .to("activemq:queue:orderIDGenIn");
                //.transform(body().append("\n"))
                //.to("file:" + DESTINATION_FOLDER + "?fileName=webordersystemoutput.txt&noop=true&fileExist=Append"); //only for debugging
            }
        });
        camelContext.start();

        while(true){
            camelContext.createProducerTemplate().sendBody("direct:start", Client.getOrderFromTerminal());
        }
        //camelContext.stop();
    }
}
