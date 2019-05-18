package com.axe.servicemiya;

import brave.sampler.Sampler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Spring Cloud Sleuth 主要功能就是在分布式系统中提供追踪解决方案，并且兼容支持了 zipkin，你只需要在pom文件中引入相应的依赖即可。
 *
 * 数显需要单独启动zipkin(通过 jar 包, 或者做成 zipkin-server 微服务注册到 eureka)
 * 注册成 zipkin-server 微服务后可以将信息记录到 mq 或者数据库,来保证高可用
 * 引入依赖以后,配置 zipkin 地址
 * 这样产生链路调度的时候就会记录到 zipkin 了
 */
@RestController
@SpringBootApplication
public class ServiceMiyaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceMiyaApplication.class, args);
    }

    private static final Logger LOG = Logger.getLogger(ServiceMiyaApplication.class.getName());

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    /**
     * 有时候可能在zipkin服务器中看不到数据，那是因为默认sleuth收集信息的比率是0.1 ，针对于这个问题有两种解决方法：
     *
     * a 在配置文件中配置 spring.sleuth.sampler.percentage=1
     * b 在代码中声明,如下代码
     */
    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }

    @RequestMapping("/hi")
    public String home() {
        LOG.log(Level.INFO, "hi is being called");
        return "hi i'm miya!";
    }

    @RequestMapping("/miya")
    public String info() {
        LOG.log(Level.INFO, "info is being called");
        return restTemplate.getForObject("http://localhost:8988/info", String.class);
    }
}
