package com.demo.testactivemq.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import javax.jms.Queue;
import javax.jms.Topic;


@Configuration
public class ActiveMQConfig {
    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;
    //点对点
    @Bean
    public Queue queue() {
        ActiveMQQueue springBootQueue = new ActiveMQQueue("springBootQueue");
        return springBootQueue;
    }

    //发布订阅
    @Bean
    public Topic topic() {
        ActiveMQTopic springBootTopic = new ActiveMQTopic("springBootTopic");
        return springBootTopic;
    }

    //连接工厂
    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory(brokerUrl);
    }

    //Queue模式
    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerQueue(ActiveMQConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        bean.setConnectionFactory(connectionFactory);
        return bean;
    }

    //Topic模式
    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerTopic(ActiveMQConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory  = new DefaultJmsListenerContainerFactory();
        //设置为发布订阅方式, 默认情况下使用的生产消费者方式
        factory .setPubSubDomain(true);
        factory .setConnectionFactory(connectionFactory);
        factory.setSessionTransacted(true);
        factory.setAutoStartup(true);
        //开启持久化订阅
        factory.setSubscriptionDurable(true);
        //重连间隔时间
        factory.setRecoveryInterval(1000L);
        factory.setClientId("springBootTopicId");
        return factory ;
    }
}

