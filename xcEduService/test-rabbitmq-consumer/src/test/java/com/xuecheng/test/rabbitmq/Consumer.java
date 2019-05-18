package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.nio.charset.StandardCharsets;

/**
 * @author LiYuan
 * Created on 2018/12/18.
 */
public class Consumer {

    //队列
    private static final String QUEUE = "HELLO WORLD";

    public static void main(String[] args) {
        //通过连接工厂创建新的连接和mq建立连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        //设置虚拟机，一个mq服务可以设置多个虚拟机，每个虚拟机相当于一个独立的mq
        connectionFactory.setVirtualHost("/");
        try {
            //和mq建立链接
            Connection connection = connectionFactory.newConnection();
            //创建会话通道
            Channel channel = connection.createChannel();

            //声明队列:如果在mq中没有则要创建
            //参数列表如下：
            //String queue: 队列名称
            //boolean durable: 是否声明为持久化队列，重启后依然存活
            //boolean exclusive: 是否声明为独有队列 ，仅限于本连接使用
            //boolean autoDelete: 是否声明为自动删除队列 ，不使用后服务器自动删除
            //Map<String, Object> arguments: 其他参数，如存活时间等
            channel.queueDeclare(QUEUE, true, false, false, null);

            //实现消费方法
            DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
                //当接收到消息后此方法被调用
                //String consumerTag: 消费者标签，可以在监听队列时设置
                //Envelope envelope: 信封，通过envelope可以拿到
                //AMQP.BasicProperties properties: 内容头信息
                //byte[] body: 请求体
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
                    byte[] body) {
                    //交换机
                    String exchange = envelope.getExchange();
                    //消息id，mq在channel中用来标记消息的id，可用于确认消息已接收
                    long deliveryTag = envelope.getDeliveryTag();
                    //消息内容
                    String message = new String(body, StandardCharsets.UTF_8);
                    System.out.println("receive message: " + message);
                }
            };

            //监听队列
            //String queue: 队列名称
            //boolean autoAck: 自动回复
            //String consumerTag: 客户端生成的用于建立上下文的消费者标记
            //boolean noLocal: 设置为true，表示不能将同一个Connection中生产者发送的消息传递给这个Connection中的消费者
            //boolean exclusive: 是否排他
            //Map<String, Object> arguments: 参数
            //Consumer callback: 消费方法
            channel.basicConsume(QUEUE, true, defaultConsumer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
