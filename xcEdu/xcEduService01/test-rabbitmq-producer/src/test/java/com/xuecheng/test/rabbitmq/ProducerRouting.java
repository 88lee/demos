package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author LiYuan
 * Created on 2018/12/23.
 */
public class ProducerRouting {

    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";

    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";

    private static final String EXCHANGE_ROUTING_INFORM = "exchange_routing_inform";

    private static final String ROUTING_KEY_EMAIL = "inform_email";

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
        Channel channel = null;
        //和mq建立链接
        try (Connection connection = connectionFactory.newConnection()) {
            //创建会话通道
            channel = connection.createChannel();
            //声明队列:如果在mq中没有则要创建
            channel.queueDeclare(QUEUE_INFORM_EMAIL, true, false, false, null);
            channel.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);
            //1、exchange:交换机名称
            //2、type:交换机类型
            //     direct: 对应工作模式 - Routing
            //     routing: 对应工作模式 - Publish/Subscribe
            //     topic: 对应工作模式 - Topics
            //     headers: 对应工作模式 - headers
            //3、durable:是否持久化,持久化的可以将交换器存盘,在服务器重启的时候不会丢失信息.
            //4、autoDelete:是否自动删除,前提是至少有一个队列或者交换器与这交换器绑定,之后所有与这个交换器绑定的队列或者交换器都与此解绑,一般都设置为false
            //5、internal:是否内置,客户端程序无法直接发送消息到这个交换器中,只能通过交换器路由到交换器的方式
            channel.exchangeDeclare(EXCHANGE_ROUTING_INFORM, BuiltinExchangeType.DIRECT);

            //队列与交换机进行绑定
            //1、String queue: 队列名称
            //2、String exchange: 交换机名称
            //3、String routingKey: 用于绑定的routingKey，相当于区分队列的标签，在发布订阅模式中设置为空字符串
            //4、Map<String, Object> arguments: 绑定的参数
            channel.queueBind(QUEUE_INFORM_EMAIL, EXCHANGE_ROUTING_INFORM, ROUTING_KEY_EMAIL);
            channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_ROUTING_INFORM, ROUTING_KEY_SMS);

            for (int i = 0; i < 5; i++) {
                //发送消息
                String messageBody = "send inform message to user";
                channel.basicPublish(EXCHANGE_ROUTING_INFORM, ROUTING_KEY_EMAIL, null, messageBody.getBytes());
                //channel.basicPublish(EXCHANGE_ROUTING_INFORM, ROUTING_KEY_SMS, null, messageBody.getBytes());
                System.out.println("send to mq " + messageBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
