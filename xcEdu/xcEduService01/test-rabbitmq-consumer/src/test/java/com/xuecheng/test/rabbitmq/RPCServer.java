package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import org.apache.commons.lang.SerializationUtils;

public class RPCServer {

    public static void main(String[] args) throws InterruptedException, IOException, TimeoutException {

        String exchangeName = "rpc_exchange";   //交换器名称
        String queueName = "rpc_queue";     //队列名称
        String routingKey = "rpc_key";  //路由键

        ConnectionFactory factory = new ConnectionFactory();
        factory.setVirtualHost("test");
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");

        final Connection connection = factory.newConnection();    //创建链接
        final Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchangeName, "direct", false, false, null);    //定义交换器
        channel.queueDeclare(queueName, false, false, false, null); //定义队列
        channel.queueBind(queueName, exchangeName, routingKey, null); //绑定队列
        QueueingConsumer consumer = new QueueingConsumer(channel);     //创建一个消费者
        channel.basicConsume(queueName, true, consumer);  //消费消息

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();  //获得一条消息
            final String correlationID = delivery.getProperties().getCorrelationId();    //获得额外携带的correlationID
            final String replyTo = delivery.getProperties().getReplyTo(); //获得回调的队列路由键
            final String body = (String) SerializationUtils.deserialize(delivery.getBody());  //获得请求的内容
            new Thread(() -> {
                Channel channel1 = null;
                try {
                    channel1 = connection.createChannel();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String responseMsg = "welcome " + body; //处理返回内容
                //返回消息时携带 请求时传过来的correlationID
                AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder().correlationId(correlationID)
                                                                                    .build();
                try {
                    if (channel1 != null) {
                        //返回处理结果
                        channel1.basicPublish("", replyTo, properties, SerializationUtils.serialize(responseMsg));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        }

    }

}
