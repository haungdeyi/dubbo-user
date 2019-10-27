package com.huangdeyi.dubbo.jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsSendTopic {

    public static void main(String[] args) {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("" + "failover:(tcp://192.168.43.66:61616,tcp://192.168.43.88:61616)");

        //得到一个连接
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            //触发连接
            connection.start();
            //创建会话（生产和消费消息单线程的上下文）
            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            //创建一个发布订阅消息（目的地，发布订阅消息（存在则不会创建）
            Destination destination = session.createTopic("first-topic");
            //创建一个消息发送者
            MessageProducer messageProducer = session.createProducer(destination);

            //创建一个文本消息
            TextMessage textMessage = session.createTextMessage("hello baby!!!");
            //发送消息
            messageProducer.send(textMessage);
            //commit
            session.commit();
            session.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            if(connection != null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
