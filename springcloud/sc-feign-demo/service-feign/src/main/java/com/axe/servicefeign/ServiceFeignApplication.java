package com.axe.servicefeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Spring Cloud从Edgware版本开始，服务注册发现功能改为只要依赖了以spring-cloud-starter-netflix为前缀的库就能开启
 * 无需再使用注解开启
 *
 * 之前的EnableDiscoveryClient注解已经不会影响服务发现注册功能了
 * 而EnableEurekaClient注解因为没有再像之前的版本一样引入EnableDiscoveryClient而失效
 *
 * Feign集成了Ribbon技术，所以也支持负载均衡（默认轮询，如需要替换可以在将自己需要的规则添加到容器中）
 *
 * 添加依赖后,还需要配置 actuator 相关属性,再配合EnableHystrixDashboard 和 EnableCircuitBreaker 注解,即可开启 HystrixDashboard
 */
@EnableHystrixDashboard
@EnableCircuitBreaker
@EnableFeignClients
@SpringBootApplication
public class ServiceFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceFeignApplication.class, args);
    }

}
