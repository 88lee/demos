package com.axe.servicefeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Spring Cloud从Edgware版本开始，服务注册发现功能改为只要依赖了以spring-cloud-starter-netflix为前缀的库就能开启
 * 无需再使用注解开启
 *
 * 之前的EnableDiscoveryClient注解已经不会影响服务发现注册功能了
 * 而EnableEurekaClient注解因为没有再像之前的版本一样引入EnableDiscoveryClient而失效
 *
 * Feign集成了Ribbon技术，所以也支持负载均衡（默认轮询，如需要替换可以在将自己需要的规则添加到容器中）
 */
@EnableFeignClients
@SpringBootApplication
public class ServiceFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceFeignApplication.class, args);
    }

}
