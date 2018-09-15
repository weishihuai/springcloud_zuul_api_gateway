package com.springcloud.wsh;

import com.springcloud.wsh.filter.AccessPasswordZuulFilter;
import com.springcloud.wsh.filter.AccessUsernameZuulFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * @Description: api网关启动类
 * @Author: WeiShiHuai
 * @Date: 2018/9/14 14:22
 * Zuul的主要功能是路由转发和过滤器
 * 通常情况下，可以在网关服务进行权限控制等，将具体业务逻辑与权限控制分开，有利于解耦; Zuul也可以实现对服务的负载均衡
 * Zuul主要作用是请求转发，和过滤，请求转发是做了负载均衡。Zuul也需要做一次集群，因为 Zuul是网关，可能需要做很复杂的逻辑，比如查数据库，还有静态资源，在最外一层需要再一个zuul或者nginx去路由。
 * 通常情况下由nginx做第一层比较好，第二层的一些例如权限等校验交由zuul处理
 * 路由配置方式： a、path-url方式  b、path-serviceId方式，推荐使用path-serviceId方式，方便后期网关服务维护
 */
@SpringBootApplication
@EnableDiscoveryClient
//@EnableZuulProxy注解用于开启Zuul路由功能(反向代理)
@EnableZuulProxy
public class SpringcloudApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudApiGatewayApplication.class, args);
    }

    @Bean
    AccessUsernameZuulFilter accessUsernameZuulFilter() {
        return new AccessUsernameZuulFilter();
    }

    @Bean
    AccessPasswordZuulFilter accessPasswordZuulFilter() {
        return new AccessPasswordZuulFilter();
    }
}
