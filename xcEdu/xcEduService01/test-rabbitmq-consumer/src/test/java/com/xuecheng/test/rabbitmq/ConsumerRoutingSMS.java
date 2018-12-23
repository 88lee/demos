package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.nio.charset.StandardCharsets;

/**
 * @author LiYuan
 * Created on 2018/12/23.
 */
public class ConsumerRoutingSMS {

    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";

    private static final String EXCHANGE_ROUTING_INFORM = "exchange_routing_inform";

    private static final String ROUTING_KEY_SMS = "inform_sms";

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
            channel.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);
            //声明交换机
            channel.exchangeDeclare(EXCHANGE_ROUTING_INFORM, BuiltinExchangeType.DIRECT);
            //队列与交换机进行绑定
            channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_ROUTING_INFORM, ROUTING_KEY_SMS);
            //实现消费方法
            DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
                //当接收到消息后此方法被调用
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
                    byte[] body) {
                    System.out.println("receive message: " + new String(body, StandardCharsets.UTF_8));
                }
            };
            //监听队列
            channel.basicConsume(QUEUE_INFORM_SMS, true, defaultConsumer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
