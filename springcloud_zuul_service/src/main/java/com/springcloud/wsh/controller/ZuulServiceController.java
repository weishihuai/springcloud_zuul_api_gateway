package com.springcloud.wsh.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: ZuulServiceController
 * @ProjectName springcloud_zuul_api_gateway
 * @Description: 对外提供接口服务
 * @Author WeiShiHuai
 * @Date 2018/9/14 14:16
 */
@RestController
public class ZuulServiceController {

    private static Logger logger = LoggerFactory.getLogger(ZuulServiceController.class);

    @Value("${server.port}")
    private String port;

    @RequestMapping("/getInfo/{name}")
    public String getInfo(@PathVariable("name") String name) {
        String info = "hello " + name + " ,i am from zuul service,port = " + port;
        logger.info(info);
        return info;
    }

}
