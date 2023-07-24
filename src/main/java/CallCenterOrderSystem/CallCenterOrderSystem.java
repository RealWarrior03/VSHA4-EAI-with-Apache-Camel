package CallCenterOrderSystem;

import ContentEnricher.*;
import WebOrderSystem.WOSInputTransformer;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.activemq.ActiveMQConnectionFactory;
import OrderMessage.*;

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
    Customer-ID, Name, Number of ordered surfboards, Number of ordered diving suits

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
            Queue queue = session.createQueue("orderIDGenIn");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread.sleep(5000);

            CamelContext camelContext = new DefaultCamelContext();
            camelContext.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    //from("file:" + foldername + "?fileName=" + filename + "&noop=true&scheduler=quartz2&scheduler.cron=0+0/2+*+*+*+?")
                    from("file:" + foldername + "?fileName=" + filename + "&delay=120000")
                            .split(body().tokenize(System.getProperty("line.separator")))
                            .process(new CCOSInputTransformer()) //transformCCOS
                            .process(new ContentEnricher())//enrich Message
                            .process(new OrderMessageToNormedStringConverter())
                            .to("activemq:queue:orderIDGenIn"); //to queue channel
                }
            });
            camelContext.start();

            //camelContext.createProducerTemplate().sendBody("direct:start", "Peter, Parker, 2, 0, 1");
    }
}
