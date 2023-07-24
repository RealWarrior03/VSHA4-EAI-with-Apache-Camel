package ResultSystem;

import OrderMessage.*;
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
                        .process(new NormedStringToOrderMessageConverter())
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
                        .process(new OrderMessageToNormedStringConverter())
                        .to("activemq:topic:resultOut")
                        .transform(body().append("\n"))
                        .to("file:src/main/outputfiles?fileName=resultOutputFile.txt&noop=true&fileExist=Append");


                //.to("activemq:topic:new_order");  //pubsub channel TODO might be incorrectly implemented
            }
        });

        camelContext.start();
        Thread.sleep(500000);
        camelContext.stop();
    }

}
