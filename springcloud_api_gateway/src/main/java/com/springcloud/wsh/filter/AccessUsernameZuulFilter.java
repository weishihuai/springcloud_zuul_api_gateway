package com.springcloud.wsh.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Title: AccessUsernameZuulFilter
 * @ProjectName springcloud_zuul_api_gateway
 * @Description: 校验用户名的Zuul过滤器
 * @Author WeiShiHuai
 * @Date 2018/9/14 15:02
 */

@Component
public class AccessUsernameZuulFilter extends ZuulFilter {

    private static Logger logger = LoggerFactory.getLogger(AccessUsernameZuulFilter.class);
    private static final String USER_NAME = "weixiaohuai";

    /**
     * 过滤器类型
     * pre:在路由之前执行  routing:在路由的时候执行  post:在路由完成之后执行  error:路由发生错误时执行
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器的执行顺序
     * 数字越大，优先级越低
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否需要过滤，true表示过滤，false表示不过滤
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器具体校验逻辑，如权限校验等等
     * 实际项目中可以进行数据库查询权限数据库等等
     *
     * @return
     */
    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        //获取请求url中的username
        String username = request.getParameter("username");

        //说明用户名不为空且正确
        if (null != username && USER_NAME.equals(username)) {
            //表示让Zuul进行路由跳转
            requestContext.setSendZuulResponse(true);
            //设置响应码
            requestContext.setResponseStatusCode(200);
            logger.info("welcome,the username is valid!!");
            //记录这个过滤器的状态
            requestContext.set("isSuccess", true);
            return null;
        } else { //用户名不正确
            //表示让Zuul过滤这个请求，不进行路由跳转
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(401);
            //记录这个过滤器的状态
            requestContext.set("isSuccess", false);
            logger.info("sorry,the username is not valid, please try again!!");
            requestContext.setResponseBody("sorry,the username is not valid, please try again!!");
            return null;
        }
    }
}
