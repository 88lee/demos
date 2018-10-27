package com.itheima;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBootApplication注解 被 @Configuration、@EnableAutoConfiguration、@ComponentScan 注解所修饰，
 * 换言之 Springboot 提供了统一的注解来替代以上三个注解，简化程序的配置。
 *
 * @author liyuan
 * @date 2018/10/27.
 */
//声明该类是SpringBoot的引导类
@SpringBootApplication
public class MySpringBootApplication {

    public static void main(String[] args) {
        //run方法:表示运行SpringBoot的引导类，参数为引导类的字节码对象
        //注意:哪个类是引导类是通过@SpringBootApplication确认的
        //一般把run方法写在引导类里
        SpringApplication.run(MySpringBootApplication.class);
    }
}
