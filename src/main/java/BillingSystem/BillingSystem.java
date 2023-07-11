package BillingSystem;

import OrderMessage.OrderMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.jupiter.api.Order;

import javax.jms.*;

// TODO anstatt string mit Messages arbeiten
// TODO nachricht richtig an Resultsystem weiterleiten
// TODO CustomerCreditStanding logik implementieren
// TODO Betrag abziehen wenn Rückmeldung von Resultsystem
// TODO CustumerCredits Datenbank oder so in Billingsystempackage machen

public class BillingSystem {

    public static void main(String[] args) throws Exception {
        CustomerCreditStanding processCCS = new CustomerCreditStanding();
        /*
        DefaultCamelContext ctxt = new DefaultCamelContext();
        ActiveMQComponent activeMQComponent = ActiveMQComponent.activeMQComponent();
        activeMQComponent.setTrustAllPackages(true);
        ctxt.addComponent("activemq", activeMQComponent);
        */
        //activemq stuff

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        connectionFactory.setTrustAllPackages(true);
        try {
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic("new_order");
            Queue resultIn = session.createQueue("resultIn");

            //MessageProducer producer = session.createProducer(topic);
            Topic topicToBeProcessed = session.createTopic("resultOut");
            MessageConsumer validOrderConsumer = session.createConsumer(topicToBeProcessed);
            validOrderConsumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    if (message instanceof ObjectMessage){
                        try {
                            OrderMessage mes = (OrderMessage) ((ObjectMessage) message).getObject();
                            //TODO wait for datastructure and manipulate it
                        } catch (JMSException e) {
                            throw new RuntimeException(e);
                        }
                    }



                }
            });



        } catch (Exception e) {
            e.printStackTrace();
        }


        CamelContext camelContext = new DefaultCamelContext();
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("activemq:topic:new_order")

                        .process(processCCS)
                        .process(exchange -> {
                            // Hole den Inhalt der Datei
                            OrderMessage content = exchange.getIn().getBody(OrderMessage.class);

                            // Gib den Inhalt in der Konsole aus
                            System.out.println("Content of the OrderMessage Object");
                            System.out.println(content.toString());
                        })


                        .to("activemq:queue:resultIn");
            }
        });

        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("activemq:topic:resultOut")

                        .process(processCCS)
                        .process(exchange -> {
                            // Hole den Inhalt der Datei
                            OrderMessage content = exchange.getIn().getBody(OrderMessage.class);

                            // Gib den Inhalt in der Konsole aus
                            System.out.println("Content of the OrderMessage Object");
                            System.out.println(content.toString());
                        })


                        .to("activemq:queue:resultIn");
            }
        });
        camelContext.start();
        //camelContext.stop();
    }
}
