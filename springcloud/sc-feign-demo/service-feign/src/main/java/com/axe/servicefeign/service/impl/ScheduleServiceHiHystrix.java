package com.axe.servicefeign.service.impl;

import com.axe.servicefeign.service.ScheduleServiceHi;
import org.springframework.stereotype.Component;

@Component
public class ScheduleServiceHiHystrix implements ScheduleServiceHi {

    @Override
    public String sayHiFromClientOne(String name) {
        return "Sorry, " + name;
    }

}
