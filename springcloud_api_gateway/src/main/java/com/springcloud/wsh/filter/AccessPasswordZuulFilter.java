package com.springcloud.wsh.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Title: MyZuulFilter
 * @ProjectName springcloud_zuul_api_gateway
 * @Description: 校验密码的Zuul过滤器
 * @Author WeiShiHuai
 * @Date 2018/9/14 15:37
 */

@Component
public class AccessPasswordZuulFilter extends ZuulFilter {

    private static Logger logger = LoggerFactory.getLogger(AccessPasswordZuulFilter.class);
    private static final String PASSWORD = "123456";

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
        return 1;
    }

    /**
     * 是否需要过滤，true表示过滤，false表示不过滤
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        //获取上一个过滤器保存的过滤器状态，如果返回false，则说明上一个过滤器没有成功，则无需执行后面的过滤器，直接返回结果
        return (boolean) requestContext.get("isSuccess");
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
        //获取请求url中的password
        String password = request.getParameter("password");

        //说明密码不为空且正确
        if (null != password && PASSWORD.equals(password)) {
            //表示让Zuul进行路由跳转
            requestContext.setSendZuulResponse(true);
            //设置响应码
            requestContext.setResponseStatusCode(200);
            //记录这个过滤器的状态
            requestContext.set("isSuccess", true);
            logger.info("welcome,the password is valid!!");
            return null;
        } else { //用户名不正确
            //表示让Zuul过滤这个请求，不进行路由跳转
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(401);
            //记录这个过滤器的状态
            requestContext.set("isSuccess", false);
            logger.info("sorry,the password is not valid, please try again!!");
            requestContext.setResponseBody("sorry,the password is not valid, please try again!!");
            return null;
        }
    }
}
