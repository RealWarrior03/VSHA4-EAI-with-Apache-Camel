package OrderIDGenerrator;

import ContentEnricher.ContentEnricher;
import OrderMessage.OrderMessage;
import WebOrderSystem.WOSInputTransformer;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import javax.jms.Connection;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

public class OrderIDGenerator {
    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        connectionFactory.setTrustAllPackages(true);
        try {
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue orderIDGenIn = session.createQueue("orderIDGenIn");
            Topic new_order = session.createTopic("new_order");
            //MessageProducer producer = session.createProducer(topic);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread.sleep(5000);

        CamelContext camelContext = new DefaultCamelContext();
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("activemq:queue:orderIDGenIn")
                        .process(new IDGenTransformer())
                        .to("activemq:topic:new_order");  //pubsub channel TODO might be incorrectly implemented
                //.transform(body().append("\n"))
                //.to("file:" + DESTINATION_FOLDER + "?fileName=webordersystemoutput.txt&noop=true&fileExist=Append"); //only for debugging
            }
        });
        camelContext.start();

        Thread.sleep(5000);

        camelContext.stop();
    }
}
