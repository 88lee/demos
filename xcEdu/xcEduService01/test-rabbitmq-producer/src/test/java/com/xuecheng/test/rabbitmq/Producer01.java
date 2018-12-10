package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * rabbitmq的入门程序
 *
 * @author LiYuan
 * Created on 2018/12/10.
 */
public class Producer01 {

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
            //发送消息
            //参数列表如下：
            //String exchange： 交换机，如果不指定将使用默认交换机,设置为""
            //String routingKey： 路由key，交换机根据路由key来将消息转发到指定的队列，如果使用默认交换机routingKey设置为队列的名称
            //BasicProperties props： 其他参数，请求头等
            //byte[] body： 消息内容
            String messageBody = "hello world 黑马程序员";
            channel.basicPublish("", QUEUE, null, messageBody.getBytes());
            System.out.println("send to mq " + messageBody);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

    }

}
