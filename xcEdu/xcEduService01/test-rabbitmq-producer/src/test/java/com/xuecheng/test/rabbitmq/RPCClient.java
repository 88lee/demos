package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeoutException;
import org.apache.commons.lang.SerializationUtils;

/**
 * @author LiYuan
 * Created on 2018/12/23.
 */
public class RPCClient {

    private static Connection connection = null;

    private Connection getConnection() throws IOException, TimeoutException {

        if (connection == null) {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setVirtualHost("test");
            factory.setHost("127.0.0.1");
            factory.setPort(5672);
            factory.setUsername("guest");
            factory.setPassword("guest");
            connection = factory.newConnection();
        }
        return connection;
    }

    private String sayHelloToServer(String username) throws IOException, InterruptedException, TimeoutException {

        String exchangeName = "rpc_exchange";   //交换器名称
        String queueName = "rpc_queue";     //队列名称
        String routingKey = "rpc_key";  //路由键

        Channel channel = getConnection().createChannel();
        channel.exchangeDeclare(exchangeName, "direct", false, false, null);    //定义交换器
        channel.queueDeclare(queueName, false, false, false, null); //定义队列
        channel.queueBind(queueName, exchangeName, routingKey, null); //绑定队列
        String callbackQueue = channel.queueDeclare().getQueue();   //获得匿名的 独立的 默认队列
        String correlationId = UUID.randomUUID().toString();    //产生一个 关联ID correlationID
        QueueingConsumer consumer = new QueueingConsumer(channel);  // 创建一个消费者对象
        channel.basicConsume(callbackQueue, true, consumer);      //消费消息

        //创建消息属性
        //携带唯一的 correlationID
        //携带callback 回调的队列路由键
        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties.Builder().correlationId(correlationId)
                                                                                 .replyTo(callbackQueue)
                                                                                 .build();
        channel.basicPublish(exchangeName, routingKey, basicProperties, SerializationUtils.serialize(username));  //发布消息
        String response;
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();   //循环获得消息
            System.out.println("delivery >>>>[user:" + username + "] >> routingKey : " + callbackQueue);
            if (correlationId.equals(
                delivery.getProperties().getCorrelationId())) {  //匹配correlationID是否与发出去的correlation的ID一直
                response = (String) SerializationUtils.deserialize(delivery.getBody()); //获得处理结果
                break;
            }

        }
        channel.close();
        //关闭链接
        return response;

    }

    public static void main(String[] args) {

        List<String> usernameList = new ArrayList<>();

        usernameList.add("TONY_A");
        usernameList.add("TONY_B");
        usernameList.add("TONY_C");
        usernameList.add("TONY_D");
        usernameList.add("TONY_E");
        usernameList.add("TONY_F");
        usernameList.add("TONY_G");

        for (final String username : usernameList) {
            new Thread(() -> {
                RPCClient client = new RPCClient();
                String response;
                try {
                    response = client.sayHelloToServer(username);
                } catch (Exception e) {
                    e.printStackTrace();
                    response = "ERROR!!!";
                }
                System.out.println("server response : " + response);
            }).start();
        }

    }

}
