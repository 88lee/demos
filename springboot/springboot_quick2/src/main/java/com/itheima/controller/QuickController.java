package com.itheima.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liyuan
 * @date 2018/10/27.
 */
//@RestController相当于封装了@Controller和@ResponseBody
@RestController
public class QuickController {

    @RequestMapping("/quick2")
    public String quick() {
        return "Hello SpringBoot";
    }
}
