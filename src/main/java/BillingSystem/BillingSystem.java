package BillingSystem;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import javax.jms.*;

public class BillingSystem {
    //TODO aggregatorInBilling erstellen
    public static void main() throws Exception {
        /*
        DefaultCamelContext ctxt = new DefaultCamelContext();
        ActiveMQComponent activeMQComponent = ActiveMQComponent.activeMQComponent();
        activeMQComponent.setTrustAllPackages(true);
        ctxt.addComponent("activemq", activeMQComponent);
        */
        //activemq stuff

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        try {
            //Getting Connection and starting it
            Connection connection = connectionFactory.createConnection();
            connection.start();
            //Starting Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Topic topic = session.createTopic("aggregatorInBilling");
            MessageProducer producer = session.createProducer(topic);
        } catch (Exception e) {
            e.printStackTrace();
        }

        CamelContext camelContext = new DefaultCamelContext();
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("activemq:topic:new_order")
                        .process(new CustomerCreditStanding())
                        .to("activemq:queue:aggregatorInBilling");
            }
        });
        camelContext.start();
        camelContext.stop();
    }
}
