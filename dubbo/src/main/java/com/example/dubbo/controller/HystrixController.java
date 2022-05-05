package com.example.dubbo.controller;

import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/hystrix-controller")
@RestController
public class HystrixController {


    @HystrixCommand(
            fallbackMethod = "command",
            commandProperties = {
                    //超时时间
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "6000"),
/*                    //超时时间
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "7000"),*/
                    //当在配置时间窗口内达到此数量的失败后，进行短路。默认20个
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "4"),
                    //出错百分比阈值，当达到此阈值后，开始短路。默认50%
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    //短路多久以后开始尝试是否恢复，默认5s
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "3000")
            }
    )
    @RequestMapping("/return-test")
    public String hystrixTest(Integer i) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Integer> list = Lists.newArrayList(2, 5, 1, 4, 6);
        System.err.println("i的值: " + i);
        System.err.println(list.get(i));
        return "正常返回";
    }

    public String command(Integer i) {
        return "熔断生效，已隔离！!!";
    }
}
