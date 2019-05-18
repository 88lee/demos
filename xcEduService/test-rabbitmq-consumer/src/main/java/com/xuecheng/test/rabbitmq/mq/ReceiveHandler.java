package com.xuecheng.test.rabbitmq.mq;

import com.rabbitmq.client.Channel;
import com.xuecheng.test.rabbitmq.config.RabbitmqConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author LiYuan
 * Created on 2018/12/23.
 */
@Component
public class ReceiveHandler {

    @RabbitListener(queues = { RabbitmqConfig.QUEUE_INFORM_EMAIL })
    public void sendEmail(String content, Message message, Channel channel) {
        System.out.println("receive message is： " + content);
    }

}
