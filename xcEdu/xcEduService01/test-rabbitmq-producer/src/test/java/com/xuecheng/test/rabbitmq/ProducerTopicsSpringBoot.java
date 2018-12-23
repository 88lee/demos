package com.xuecheng.test.rabbitmq;

import com.xuecheng.test.rabbitmq.config.RabbitmqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author LiYuan
 * Created on 2018/12/23.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProducerTopicsSpringBoot {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void testSendEmail() {
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TOPIC_INFORM, "inform.email",
            "send email message to user");
    }
}
