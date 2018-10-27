package com.itheima.controller;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 使用@ConfigurationProperties注解，并指定前缀，可自动匹配其属性到成员变量（需有getter/setter方法）中
 *
 * @author liyuan
 * @date 2018/10/27.
 */
@Controller
@ConfigurationProperties(prefix = "person")
public class Quick3Controller {

    private String name;
    private String addr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    @RequestMapping("/quick3")
    @ResponseBody
    public String quick2() {
        return "name:" + name + " | person.addr:" + addr;
    }
}
