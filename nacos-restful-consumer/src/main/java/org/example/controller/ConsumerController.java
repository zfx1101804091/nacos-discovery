package org.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * @description:
 *  TODO 简单restful接口实现
 * @author: zheng-fx
 * @time: 2020/3/17 0017 08:27
 */
@RestController
public class ConsumerController {
    
   /* 
    @Value("${provider.address}")
    private String provider;*/
    
   //通过负载均衡发现地址
    @Autowired
    LoadBalancerClient loadBalancerClient;
    
   /* @GetMapping(value = "/server")
    public String server(){
        String url = "http://"+provider+"/server";
        //调用接口，返回的String类型，所以此处为String.class
        String result = new RestTemplate().getForObject(url, String.class);

        return "Consumer invoke success  "+result;
    }
*/
    //服务id即注册中心的中的服务名 
    private String serviceId="nacos-restful-provider";
   
    
    @GetMapping(value = "/server1")
    public String server1(){

        //发现一个地址
        ServiceInstance choose = loadBalancerClient.choose(serviceId);
        //获取一个http://ip+端口的地址
        URI uri = choose.getUri();
        //调用服务
        String result = new RestTemplate().getForObject(uri+"/server", String.class);
        System.out.println("调用的服务URI---"+uri);
        return "Consumer invoke success  "+result;
    }
}
