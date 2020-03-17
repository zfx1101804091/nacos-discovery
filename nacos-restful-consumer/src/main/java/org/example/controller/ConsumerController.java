package org.example.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @description:
 *  TODO 简单restful接口实现
 * @author: zheng-fx
 * @time: 2020/3/17 0017 08:27
 */
@RestController
public class ConsumerController {
    
    @Value("${provider.address}")
    private String provider;
    
    @GetMapping(value = "/server")
    public String server(){
        String url = "http://"+provider+"/server";
        //调用接口，返回的String类型，所以此处为String.class
        String result = new RestTemplate().getForObject(url, String.class);

        return "Consumer invoke success  "+result;
    }
}
