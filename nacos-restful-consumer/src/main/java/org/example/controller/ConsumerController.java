package org.example.controller;

import com.zfx.dubbo.service.Server1Api;
import com.zfx.dubbo.service.Server2Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.ConfigurableApplicationContext;
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
    
    //通过dubbo服务注入
    @org.apache.dubbo.config.annotation.Reference
    Server2Api server2Api;
    
    @org.apache.dubbo.config.annotation.Reference
    Server1Api server1Api;
    
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
    
    @GetMapping(value="/server2")
    public String dubboServer(){
        String server2 = server2Api.dubboServer2();
        return "dubbo服务调用----"+server2;
    }


    @GetMapping(value="/server3")
    public String dubboServer3(){
        String server2 = server1Api.dubboServer1();
        return "dubbo服务调用----"+server2;
    }
    
    /*
     * 功能描述: 
     *   TODO 注意：要使用配置中心就要在bootstrap.yml中来配置，bootstrap.yml配置文件的加载顺序要比application.yml要 优先。
     *    @Value注解不具备实时更新配置（这里可以使用配置文件上下文ConfigurableApplicationContext实时获取） 
     * @Param: 
     * @Return: 
     * @Author: Administrator
     * @Date: 2020/3/19 0019 0:01
     */
    
    @Value("${common.name}")
    private String commonName;
    
    @Autowired
    ConfigurableApplicationContext configurableApplicationContext;
    
    //配置中心使用
    @GetMapping(value="/config")
    public String config(){
        //return commonName;
        String property = configurableApplicationContext.getEnvironment().getProperty("common.name");
        return "common.name: "+property;
    }

    @GetMapping(value="/config2")
    public String config2(){
        String name = configurableApplicationContext.getEnvironment().getProperty("common.name");
        String addr = configurableApplicationContext.getEnvironment().getProperty("common.addr");
        return "common.name: "+name+" | "+addr;
    }
}
