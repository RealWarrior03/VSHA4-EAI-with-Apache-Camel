import OrderMessage.OrderMessage;
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
            Queue resultIn = session.createQueue("resultIn");
        } catch (Exception e) {
            e.printStackTrace();
        }

        CamelContext camelContext = new DefaultCamelContext();
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("activemq:queue:resultIn")
                        .aggregate(header("OrderID"), new AggregationStrategy() { // groups by OrderID, If both messages are valid let the first one through if not set valid to false and let the first one through(It will get filtered out)
                            @Override
                            public Exchange aggregate(Exchange exchange1, Exchange exchange2) {
                                OrderMessage om1 = exchange1.getIn().getBody(OrderMessage.class);
                                OrderMessage om2 = exchange2.getIn().getBody(OrderMessage.class);
                                if(!(om1.isValid()&& om2.isValid())){
                                    om1.setValid(false);
                                    om1.setValidationResult(om1.getValidationResult()+om2.getValidationResult());
                                }
                                exchange1.getIn().setBody(om1);
                                return exchange1;
                            }
                        })
                        .log(body().toString())
                        .end();
                        //.to("activemq:topic:new_order");  //pubsub channel TODO might be incorrectly implemented
            }
        });
        camelContext.start();
    }

}
