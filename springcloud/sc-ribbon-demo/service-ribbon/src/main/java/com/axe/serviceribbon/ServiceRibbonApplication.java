package com.axe.serviceribbon;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 使用 EnableHystrix 注解开启断路器
 * 使用 EnableHystrixDashboard 开启 Hystrix 仪表盘
 */
@EnableHystrixDashboard
@EnableHystrix
@SpringBootApplication
public class ServiceRibbonApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceRibbonApplication.class, args);
    }

    /**
     * Ribbon + RestTemplate是Spring Cloud两种服务调用方式中的一种，另一种方式为feign
     * LoadBalanced 注解可用于开启负载均衡功能
     */
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * springboot 2.0以后需要配置以下 Servlet 来启动 HystrixDashboard
     *
     * http://localhost:8764/hystrix 为控制台路径
     * http://localhost:8764/hystrix.stream 为进入 Dashboard 的地址
     * @return 注册配置
     */
    @Bean
    public ServletRegistrationBean getServlet(){
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        //Single Hystrix App: http://hystrix-app:port/hystrix.stream
        //修改 urlMappings 属性可以更改进入 Dashboard 的地址
        registrationBean.addUrlMappings("/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }

}