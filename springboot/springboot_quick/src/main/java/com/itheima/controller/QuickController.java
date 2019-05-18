package com.itheima.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author liyuan
 * @date 2018/10/27.
 */
@Controller
public class QuickController {

    @RequestMapping("/quick")
    @ResponseBody
    public String quick() {
        return "Hello SpringBoot!";
    }
}
