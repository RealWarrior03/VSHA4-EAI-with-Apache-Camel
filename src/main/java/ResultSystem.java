import WebOrderSystem.ContentEnricher;
import WebOrderSystem.WOSInputTransformer;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import javax.jms.Connection;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

public class ResultSystem {
    CamelContext camelContext = new DefaultCamelContext();

    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        connectionFactory.setTrustAllPackages(true);
        try {
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue billQ = session.createQueue("billIn");
            Queue invQ = session.createQueue("invIn");
        } catch (Exception e) {
            e.printStackTrace();
        }

        CamelContext camelContext = new DefaultCamelContext();
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("activemq:queue:billIn")
                        .aggregate(header("group"), new AggregationStrategy() {
                            @Override
                            public Exchange aggregate(Exchange exchange, Exchange exchange1) {
                                return null;
                            }
                        })
                        .to("activemq:topic:new_order");  //pubsub channel TODO might be incorrectly implemented
            }
        });
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("activemq:queue:invIn")
                        .to("activemq:topic:new_order");  //pubsub channel TODO might be incorrectly implemented

            }
        });

        camelContext.start();
    }

}
