package com.zfx.dubbo.service;

import com.zfx.dubbo.service.Server2Api;
import org.apache.dubbo.config.annotation.Service;

/**
 * @description:
 * @author: zheng-fx
 * @time: 2020/3/18 0018 19:05
 */
@Service
public class Server2ApiImpl implements Server2Api {
    
    public String dubboServer2() {
        return "dubboServer2";
    }
}
