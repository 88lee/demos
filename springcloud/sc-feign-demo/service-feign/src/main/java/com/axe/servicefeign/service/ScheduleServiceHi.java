package com.axe.servicefeign.service;

import com.axe.servicefeign.service.impl.ScheduleServiceHiHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * FeignClient
 * value用来指定映射的服务名
 * fallback用来指定熔断方法
 *
 */
@FeignClient(value = "eureka-client", fallback = ScheduleServiceHiHystrix.class)
public interface ScheduleServiceHi {

    /**
     * RequestMapping用来执行具体的Rest接口
     */
    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    String sayHiFromClientOne(@RequestParam(value = "name") String name);

}
