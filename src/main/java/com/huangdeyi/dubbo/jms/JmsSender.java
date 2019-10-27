package com.huangdeyi.dubbo.jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsSender {
    public static void main(String[] args) {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("" + "tcp://192.168.43.66:61616");

        //得到一个连接
        Connection connection = null;
        try {
            //设置发送异步消息(不用等待broker的回应)
            //((ActiveMQConnectionFactory)connectionFactory).setUseAsyncSend(true);

            //设置窗口大小回执(窗口满之后必须等待broker的响应才能继续发送消息)
            //((ActiveMQConnectionFactory)connectionFactory).setProducerWindowSize(20);

            connection = connectionFactory.createConnection();
            //触发连接
            connection.start();
            //创建会话（生产和消费消息单线程的上下文）
            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            //创建一个消息队列（目的地）（队列存在则不会创建）
            //参数customer.preFetchSize=100设置borker向customer一次推送（push）消息的条数
            Destination destination = session.createQueue("first-queue?customer.preFetchSize=100");
            //创建一个消息发送者
            MessageProducer messageProducer = session.createProducer(destination);
            //创建一个文本消息（消息分为持久化消息和非持久化消息两种）
            //TextMessage textMessage = session.createTextMessage("hello baby!!!");

            //循环发送
            for(int i=0;i<2;i++){
                TextMessage textMessage = session.createTextMessage(i + ":hello baby!!!");
                messageProducer.send(textMessage);
            }
            //发送消息
            //messageProducer.send(textMessage);
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
