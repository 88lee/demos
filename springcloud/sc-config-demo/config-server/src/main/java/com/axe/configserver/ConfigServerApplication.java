package com.axe.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * http请求地址和资源文件映射如下:
 *
 * 请求: /{application}/{profile}[/{label}]
 * 例子: /foo/dev/[master]
 *
 * 文件: /{application}-{profile}.yml
 *      /{label}/{application}-{profile}.yml
 *      /{application}-{profile}.properties
 *      /{label}/{application}-{profile}.properties
 *
 * 由文件名为 config-client-dev.properties 的文件, 可以确定 application 为 config-client, profile 为 dev
 * 访问http://localhost:8888/foo/dev 相当于查找 searchPaths 下的 profile 为 dev 的文件, 然后再在文件中的找到属性名为 foo 的属性
 * 可以读取映射到文件里的名称为foo的属性的值(profile为dev)
 * 问：不同文件，同一个属性名，如何选择
 */
@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }

}
