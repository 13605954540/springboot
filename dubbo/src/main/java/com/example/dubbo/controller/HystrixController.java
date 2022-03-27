package com.example.dubbo.controller;

import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/hystrix-controller")
@RestController
public class HystrixController {


    @HystrixCommand(
            fallbackMethod = "command",
            commandProperties = {
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "50000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "4"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "25"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "3000")
            }
    )
    @RequestMapping("/return-test")
    public String hystrixTest() {
        List<Integer> list = Lists.newArrayList(2, 5);
        System.err.println(list.get(2));
        return "正常返回";
    }

    public String command() {
        return "熔断生效，已隔离！!!";
    }
}
