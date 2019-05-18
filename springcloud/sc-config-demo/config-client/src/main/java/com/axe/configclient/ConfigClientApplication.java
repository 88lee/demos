package com.axe.configclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RefreshScope是spring cloud提供的一种特殊的scope实现，用来实现配置、实例热加载。
 *
 * 可以通过发送,POST请求到http://localhost:8881/actuator/bus-refresh来实现配置文件的刷新
 * spring-cloud-starter-bus-amqp 依赖于 RabbitMq
 */
@RefreshScope
@RestController
@SpringBootApplication
public class ConfigClientApplication {

    /**
     *
     */

    public static void main(String[] args) {
        SpringApplication.run(ConfigClientApplication.class, args);
    }

    @Value("${foo}")
    String foo;

    @RequestMapping(value = "/hi")
    public String hi() {
        return foo;
    }
}
