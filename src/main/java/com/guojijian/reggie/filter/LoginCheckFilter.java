package com.guojijian.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.guojijian.reggie.commons.R;
import com.guojijian.reggie.commons.contants.Contants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.soap.Addressing;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
@Slf4j
public class LoginCheckFilter implements Filter {

    //路径匹配器，可识别占位符
    private static final AntPathMatcher PATH_MATCHER=new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("登录验证过滤器执行中...");
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;

        //1、获取请求路径
        String requestURI=request.getRequestURI();

        //2、判断是否需要过滤当前请求路径
        String[] uris=new String[]{
                "/backend/**",
                "/front/**",
                "/employee/login",
                "/employee/logout"
        };
        for(String uri:uris){
            if(PATH_MATCHER.match(uri,requestURI)){
                filterChain.doFilter(request,response);
                return;
            }
        }

        log.info("拦截路径为：{}",requestURI);

        //3、验证是否登录
        Object user=request.getSession().getAttribute(Contants.SESSION_USER);
        if(user==null){
            response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
            return;
        }

        //4、已登录，放行
        filterChain.doFilter(request,response);
    }
}
