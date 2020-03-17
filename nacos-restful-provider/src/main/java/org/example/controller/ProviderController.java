package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: zheng-fx
 * @time: 2020/3/17 0017 08:20
 */
@RestController
public class ProviderController {
    
    @GetMapping(value = "/server")
    public String providerServer(){
        return "providerServer,invoke success";
    }
}
