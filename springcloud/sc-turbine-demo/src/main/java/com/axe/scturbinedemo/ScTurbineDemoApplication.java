package com.axe.scturbinedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.web.bind.annotation.RestController;

/**
 * 主要用于聚合多个 HystrixDashboard 的数据, 注意依赖和配置
 *
 * RestController 注解是必须的, 否则报错Caused by: org.apache.http.ProtocolException: Invalid header: HTTP/1.1 200
 *
 * turbine 的流为 http://127.0.0.1:8766/turbine.stream , 注意与 Hystrix 的进行区分
 */
@EnableHystrix
@EnableHystrixDashboard
@EnableCircuitBreaker
@EnableTurbine
@RestController
@SpringBootApplication
public class ScTurbineDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScTurbineDemoApplication.class, args);
    }

}
