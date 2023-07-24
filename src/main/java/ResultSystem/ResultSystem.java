package ResultSystem;

import OrderMessage.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import PrintDebug.PrintDebug;
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
                        //.log("-------------INPUT------------")
                        //.process(new PrintDebug())
                        /*.process(new Processor() {
                            @Override
                            public void process(Exchange exchange) throws Exception {
                                OrderMessage om = exchange.getIn().getBody(OrderMessage.class);
                                exchange.getIn().setHeader("OrderID", om.getOrderID());
                            }
                        })*/
                        .aggregate(body().method("getOrderID"), new AggregationStrategy() {
                            @Override
                            public Exchange aggregate(Exchange exchange1, Exchange exchange2) {
                                if(exchange1 == null){
                                    return exchange2;
                                }
                                OrderMessage om1 = exchange1.getIn().getBody(OrderMessage.class);
                                OrderMessage om2 = exchange2.getIn().getBody(OrderMessage.class);
                                om1.setValid(om1.isValid() && om2.isValid());
                                om1.setValidationResult(om1.getValidationResult()+" and "+om2.getValidationResult());
                                om1.setResSysWasHere(true);
                                exchange1.getIn().setBody(om1);
                                return exchange1;
                            }
                        })
                        .completionSize(2)
                        .log("-------------OUTPUT------------")
                        .process(new PrintDebug())
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
