package com.demo.testactivemq1.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MyProducer {
    private static final String ACTIVEMQ_URL = "tcp://192.168.120.3:61616";

    public static void main(String[] args) throws JMSException {

        new MyProducer().getProducer("queue1");

    }
    public MessageProducer getProducer(String queueName) throws JMSException {
        //创建连接工厂
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //创建连接
        Connection connection = factory.createConnection();
        //打开链接
        connection.start();
        //创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建队列目标,并标识队列名称,消费者根据队列名称就收数据
        Destination destination = session.createQueue(queueName);
        //创建一个生产者
        MessageProducer producer = session.createProducer(destination);

        //向队列推送10条文本消息数据
        for (int i=1;i<=10;i++){
            //创建文本消息
            TextMessage textMessage = session.createTextMessage("第"+i+"个文本消息");
            //发送消息
            producer.send(textMessage);
            //在本地打印
            System.out.println("已发送消息: "+textMessage.getText());
        }
        //关闭连接
        connection.close();
        return producer;
    }
}
