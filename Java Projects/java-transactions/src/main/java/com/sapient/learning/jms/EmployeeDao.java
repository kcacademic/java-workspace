package com.sapient.learning.jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class EmployeeDao {
    private static String CONNECTION_URL = "tcp://localhost:61616"; //"vm://localhost";
    private Connection connection;

    public EmployeeDao() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(CONNECTION_URL);
        connection = connectionFactory.createConnection();
        connection.start();
    }

    public void sendMessage(String message) throws JMSException {
        Session session = null;
        Destination destination = null;
        MessageProducer producer = null;
        try {
            session = connection.createSession(true, 0);
            destination = session.createTopic("TEST.FOO");
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            String text = message + " From: " + Thread.currentThread().getName() + " : " + this.hashCode();
            TextMessage textMessage = session.createTextMessage(text);
            System.out.println("Sent message: " + message);
            producer.send(textMessage);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            System.out.println("Caught: " + e);
            e.printStackTrace();
        } finally {
            producer.close();
            session.close();
        }
    }

    public void receiveMessage() throws JMSException {
        Session session = null;
        Destination destination = null;
        MessageConsumer consumer = null;
        try {
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createTopic("TEST.FOO");
            consumer = session.createConsumer(destination);
            while (true) {
                Message message = consumer.receive(1000);
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    String text = textMessage.getText();
                    System.out.println("Received: " + text);
                }
            }
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        } finally {
            consumer.close();
            session.close();
        }
    }

}
