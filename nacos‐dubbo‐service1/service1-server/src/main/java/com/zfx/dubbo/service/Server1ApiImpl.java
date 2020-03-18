package com.zfx.dubbo.service;

import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

/**
 * @description:
 * @author: zheng-fx
 * @time: 2020/3/18 0018 19:05
 */
@Service
public class Server1ApiImpl implements Server1Api {
    
    @Reference
    Server2Api server2Api;
    
    public String dubboServer1() {
        String dubboServer2 = server2Api.dubboServer2();
        return "dubboService1|"+dubboServer2;
    }
}
