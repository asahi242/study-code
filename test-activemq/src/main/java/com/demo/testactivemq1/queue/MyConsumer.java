package com.demo.testactivemq1.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MyConsumer {
    private static final String ACTIVEMQ_URL = "tcp://192.168.120.3:61616";

    public static void main(String[] args) throws JMSException {
        new MyConsumer().getConsumer("queue1");
    }
    public void getConsumer(String queueName) throws JMSException {
        // 创建连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 创建连接
        Connection connection = activeMQConnectionFactory.createConnection();
        // 打开连接
        connection.start();
        // 创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 创建队列目标,并标识队列名称，消费者根据队列名称接收数据
        Destination destination = session.createQueue(queueName);
        // 创建消费者
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(message -> {
            TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println("消费消息: "+ textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
    }
}
