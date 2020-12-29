## 基础知识
### 定义
> ActiveMQ是一种开源的基于JMS（Java Message Servie）规范的一种消息中间件的实现，ActiveMQ的设计目标是提供标准的，面向消息的，能够跨越多语言和多系统的应用集成消息通信中间件。
### 常用消息服务应用
1. ActiveMQ: ActiveMQ 是 Apache 出品，最流行的，能力强劲的开源消息总线。ActiveMQ 是一个完全支持 JMS1.1 和 J2EE 1.4 规范的 JMS Provider 实现。
2. RabbitMQ: RabbitMQ 是一个在 AMQP 基础上完成的，可复用的企业消息系统。他遵循 Mozilla Public License 开源协议。开发语言Erlang。
3. RocketMQ: 由阿里巴巴定义开发的一套消息队列应用服务。

### 应用场景
&emsp;&emsp;消息队列的主要特点是==异步处理==，主要目的是==减少请求响应时间和解耦==。所以主要的使用场景就是将比较耗时而且==不需要即时==返回结果的操作作为消息放入消息队列。同时由于使用了消息队列，只要保证消息格式不变，消息的发送方和接收方并不需要彼此联系，也不需要受对方的影响，即解耦和。
![image](https://img-blog.csdnimg.cn/20191010170249930.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQyMDMxNjUz,size_16,color_FFFFFF,t_70)
### 什么是JMS
&emsp;&emsp;JMS（Java Messaging Service）是 Java 平台上有关面向消息中间件的技术规范，它便于消息系统中的 Java 应用程序进行消息交换,并且通过提供标准的产生、发送、接收消息的接口，简化企业应用的开发。
### 点对点模型(Point TO Point)
> 生产者发送一条消息到 queue，只有一个消费者能收到。

![image](https://img-blog.csdnimg.cn/20191010170344266.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQyMDMxNjUz,size_16,color_FFFFFF,t_70)
### 发布/订阅模型(Publish/Subscribe)
> 发布者发送到 topic 的消息，只有订阅了 topic 的订阅者才会收到消息。
![image](https://img-blog.csdnimg.cn/20191010170411603.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQyMDMxNjUz,size_16,color_FFFFFF,t_70)
## Linux安装ActiveMQ
### ActiveMQ安装
> ActiveMQ 官网： http://activemq.apache.org
ActiveMQ5.10.x 以上版本必须使用 JDK1.8 才能正常使用。
ActiveMQ5.9.x 及以下版本使用 JDK1.7 即可正常使用。

1. 下载安装包

```bash
wget https://archive.apache.org/dist/activemq/5.10.2/apache-activemq-5.10.2-bin.tar.gz
```
2. 解压
```bash
tar -zxvf apache-activemq-5.10.2-bin.tar.gz
```
3. 查看权限
```bash
ls -al apache-activemq-5.10.2/bin
//如果权限不足,则无法执行,需要修改文件权限
chmod 755 activemq
```
4. 复制应用至本地目录
```bash
cp -r apache-activemq-5.10.2 /usr/local/activemq
```
5. 启动ActiveMQ
```bash
/usr/local/activemq/bin/activemq start
```
6. 测试ActiveMQ
```bash
ps aux | grep activemq
```
出现下图表示启动成功

![image](https://img-blog.csdnimg.cn/20191010170602432.png)
7. 访问界面
> http://192.168.120.3:8161/admin<br>
> 用户名:admin<br>
> 密&emsp;码:admin
8. 修改访问端口
```xml
//vi /usr/local/activemq/conf/jetty.xml
<bean id="jettyPort" class="">
    <proerty name="port" name="8161"/><!--修改name值-->
</bean>
//保存并重新启动
// /usr/local/activemq/bin/activemq restart
```
9. 修改用户名和密码
```bash
vi /usr/local/activemq/conf/users.properties
//admin=admin
// /usr/local/qctivemq/bin/activemq restart
```
## ActiveMQ介绍
### 配置文件activemq.xml
> 配置文件中,配置的是 ActiveMQ 的核心配置信息. 是提供服务时使用的配置. 可以修改启动的访问端口. 即 java 编程中访问ActiveMQ 的访问端口.<br>
默认端口为:61616<br>
使用协议是:tcp协议<br>
修改端口后,保存并重启ActiveMQ服务

### ActiveMQ目录介绍
1. bin: 存放的是脚本文件
2. conf: 存放的是基本配置文件
3. data: 存放的是日志文件
4. docs: 存放的是说明文档
5. examples: 存放的是简单实例
6. libs: 存放的是activemq所需的jar包
7. webapps: 用于存放项目的目录

### ActiveMQ术语
1. Destination(目的地): JMS Provider（消息中间件）负责维护，用于对 Message 进行管理的对象。 <br>
MessageProducer 需要指定 Destination 才能发送消息，MessageReceiver 需要指定 Destination 才能接收消息。
2. Producer(消息生产者): 负责发送Message到目的地。
3. Consumer/Prceiver(消息消费者): 负责从目的地中消费【处理|监听|订阅】Message。
4. Message(消息): 消息封装一次通信的内容。

## Java操作ActiveMQ
### ActiveMQ常用API简介
> 下述 API 都是接口类型,由定义在 javax.jms 包中.是 JMS 标准接口定义.

1. ConnectionFactory(链接工厂): 用于创建连接的工厂类型
2. Connection(链接): 用于建立访问ActiveMQ链接的类型,由链接工厂创建
3. Session(会话): 一次持久有效有状态的访问,由链接创建
4. Destination/Queue(目的地): 用于描述本次访问 ActiveMQ 的消息访问目的地. 即 ActiveMQ 服务中的具体队列. 由会话创建. interface Queue extends Destination
5. MessageProducer(消息生产者): 在一次有效会话中, 用于发送消息给 ActiveMQ 服务的工具. 由会话创建.
6. MessageComsumer(消息[消费/订阅/处理]者): 在一次有效会话中, 用于从 ActiveMQ 服务中获取消息的工具. 由会话创建.
7. Message(消息):  通过消息生成者向 ActiveMQ 服务发送消息时使用的数据载体对象或消息消费者从 ActiveMQ 服务中获取消息时使用的数据载体对象. 是所有消息(文本消息，对象消息)等 具体类型的顶级接口. 可以通过会话创建或通过会话从 ActiveMQ 服务中获取.

### java代码

#### 引入依赖
```xml
    <dependency>
        <groupId>org.apache.activemq</groupId>
        <artifactId>activemq-all</artifactId>
        <version>5.9.0</version>
    </dependency>
```
#### 队列生产者
```Java
public class MyProducer {
    private static final String ACTIVEMQ_URL = "tcp://192.168.120.3:61616";

    public static void main(String[] args) throws JMSException {

        //向队列推送10条文本消息数据
        for (int i=1;i<=10;i++){
            new MyProducer().getProducer("queue1","第"+i+"个文本消息");
        }

    }
    public MessageProducer getProducer(String queueName,String text) throws JMSException {
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
        //创建文本消息
        TextMessage textMessage = session.createTextMessage(text);
        //发送消息
        producer.send(textMessage);
        //在本地打印
        System.out.println("已发送消息: "+textMessage.getText());
        //关闭连接
        connection.close();
        return producer;
    }
}
```

#### 队列消费者
```java
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
```

#### 主题生产者
```Java
public class MyProducer {
    private static final String ACTIVEMQ_URL = "tcp://192.168.120.3:61616";

    public static void main(String[] args) throws JMSException {
        //向队列推送10条文本消息数据
        for (int i=1;i<=10;i++){
            new com.demo.testactivemq1.queue.MyProducer().getProducer("topic1","第"+i+"个文本消息");
        }
    }
    public MessageProducer getProducer(String topicName,String text) throws JMSException {
        //创建连接工厂
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //创建连接
        Connection connection = factory.createConnection();
        //打开链接
        connection.start();
        //创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建队列目标,并标识队列名称,消费者根据队列名称就收数据
        Destination destination = session.createTopic(topicName);
        //创建一个生产者
        MessageProducer producer = session.createProducer(destination);
        //创建文本消息
        TextMessage textMessage = session.createTextMessage(text);
        //发送消息
        producer.send(textMessage);
        //在本地打印
        System.out.println("已发送消息: "+textMessage.getText());
        //关闭连接
        connection.close();
        return producer;
    }
}
```
#### 主题消费者
```java
public class MyConsumer {
    private static final String ACTIVEMQ_URL = "tcp://192.168.120.3:61616";

    public static void main(String[] args) throws JMSException {
        new com.demo.testactivemq1.queue.MyConsumer().getConsumer("topic1");
    }
    public void getConsumer(String topicName) throws JMSException {
        // 创建连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 创建连接
        Connection connection = activeMQConnectionFactory.createConnection();
        // 打开连接
        connection.start();
        // 创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 创建队列目标,并标识队列名称，消费者根据队列名称接收数据
        Destination destination = session.createTopic(topicName);
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
```
> 队列与主题的区别:<br>
> 队列:<br>&emsp;&emsp;先启动生产者,后启动消费者,消费者能接收到生产者产生的数据; 如果同时启动两个消费者,两个消费者轮流获取数据<br>
>主题:<br>&emsp;&emsp;必须先启动消费者,消费者才能接收生产者产生的数据; 如果同时启动两个消费者,两个消费者都能够完整的就收数据

### SpringBoot代码

#### 引入依赖
```xml
<!--消息队列连接池-->
    <dependency>
        <groupId>org.apache.activemq</groupId>
        <artifactId>activemq-pool</artifactId>
        <version>5.15.9</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-activemq</artifactId>
    </dependency>
```

#### application. properties
```properties
spring.activemq.broker-url=tcp://192.168.120.3:61616
spring.activemq.user=admin
spring.activemq.password=admin
#true 表示使用内置的MQ，false则连接服务器
spring.activemq.in-memory=false
#true表示使用连接池；false时，每发送一条数据创建一个连接
spring.activemq.pool.enabled=true
#连接池最大连接数
spring.activemq.pool.max-connections=10
#空闲的连接过期时间，默认为30秒
spring.activemq.pool.idle-timeout=30000
```
#### ActiveMQ配置文件
```java
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
```

#### 生产者(Controller)
```java
@RestController
public class ProducerController {
    //Queue模式
    @Autowired
    private MyQueue queue;
    //Topic
    @Autowired
    private MyTopic topic;
    //注入springboot封装的工具类
    @Autowired
    private JmsMessagingTemplate jms;

    /**
     * 队列模式（queue）发送消息
     * @return
     */
    @GetMapping("sendMessage")
    public String sendMessage(String queueName) throws JMSException {
        queue.setQueueName(queueName);
        //发送消息至消息中间件代理(Borker)
        jms.convertAndSend(queue,"testQueue");
        return "queue success";
    }

    /**
     * 订阅模式（topic）发送消息
     * @return
     */
    @GetMapping("/topicSend")
    public String topicSend(String topicName) {
        topic.setTopicName(topicName);
        jms.convertAndSend(topic, "testTopic");
        return "topic success";
    }
}
```
#### 自定义队列
```java
/**
 * 设置自己的Queue
 */
@Component
public class MyQueue implements Queue {
    private String queueName;
    public void setQueueName(String queueName){
        this.queueName = queueName;
    }
    @Override
    public String getQueueName() {
        return this.queueName;
    }
}

```

#### 自定义主题
```java
/**
 * 设置自己的Topic
 */
@Component
public class MyTopic implements Topic {
    private String topicName;

    public void setTopicName(String topicName){
        this.topicName = topicName;
    }
    @Override
    public String getTopicName() throws JMSException {
        return this.topicName;
    }
}

```
#### 队列消费者
```java
@Component
public class QueueConsumer {

    @JmsListener(destination = "testQueue",containerFactory = "jmsListenerContainerQueue")
    public void receiveQueue(String text){
        System.out.println("ConsumerQueue接收到的消息为:"+text);
    }
}
```
#### 主题消费者
```java
@Component
public class TopicConsumer {
    @JmsListener(destination = "springBootTopic",containerFactory = "jmsListenerContainerTopic")
    public void receiveTopic(String text){
        System.out.println("ConsumerTopic接收到的消息为:"+text);
    }
}
```
#### <span onMouseOver="this.style.color='#0F0'">入口程序</span>
```java
@SpringBootApplication
@EnableJms //启动消息队列
public class TestActivemqApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestActivemqApplication.class, args);
    }

}
```
