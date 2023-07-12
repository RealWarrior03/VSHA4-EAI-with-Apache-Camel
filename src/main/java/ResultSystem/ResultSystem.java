package ResultSystem;

import OrderMessage.OrderMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import javax.jms.*;

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
            Topic resultOut = session.createTopic("resultOut");
            MessageProducer resultProducer = session.createProducer(resultOut);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread.sleep(5000);

        CamelContext camelContext = new DefaultCamelContext();
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("activemq:queue:resultIn")
                        /*.aggregate(header("OrderID"), new AggregationStrategy() { // groups by OrderID, If both messages are valid let the first one through if not set valid to false and let the first one through(It will get filtered out)
                            @Override
                            public Exchange aggregate(Exchange exchange1, Exchange exchange2) {
                                OrderMessage om1 = exchange1.getIn().getBody(OrderMessage.class);
                                OrderMessage om2 = exchange2.getIn().getBody(OrderMessage.class);
                                if (!(om1.isValid() && om2.isValid())) {
                                    om1.setValid(false);
                                    om1.setValidationResult(om1.getValidationResult() + om2.getValidationResult());
                                }
                                om1.setResSysWasHere(true);
                                exchange1.getIn().setBody(om1);
                                return exchange1;
                            }
                        })*/
                        .process(new Processor() { // Temporary to test the sys w/o inventory TODO: Remove
                            @Override
                            public void process(Exchange exchange) throws Exception {
                                OrderMessage om1 = exchange.getIn().getBody(OrderMessage.class);
                                om1.setResSysWasHere(true);
                                om1.setValid(true);
                                exchange.getIn().setBody(om1);
                            }
                        })
                        .log(body().toString())
                        .to("activemq:topic:resultOut");


                //.to("activemq:topic:new_order");  //pubsub channel TODO might be incorrectly implemented
            }
        });

        camelContext.start();
        Thread.sleep(500000);
        camelContext.stop();
    }

}
