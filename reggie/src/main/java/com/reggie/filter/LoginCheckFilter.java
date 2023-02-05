package com.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.reggie.common.BaseContext;
import com.reggie.common.R;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 验证是否登录
 */
@WebFilter(filterName = "LoginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    //路径通配器，支持通配符（因为直接equals路径和通配符是无法匹配的）
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();
        //先判断是否登录
        //员工登录
        if (session.getAttribute("employee") != null) {
            //将id存进线程里面
            BaseContext.setCurrentId((Long) session.getAttribute("employee"));
            filterChain.doFilter(request, response);
            return;
        }
        //用户登录
        else if(session.getAttribute("user")!=null){
            //将id存进线程里面
            BaseContext.setCurrentId((Long) session.getAttribute("user"));
            filterChain.doFilter(request, response);

        } else { //未登录
            //定义不需要处理的请求路径
            String requestURI = request.getRequestURI();
            String[] urls = new String[]{
                    "/employee/login",
                    "/employee/logout",
                    "/backend/**",
                    "/front/**",
                    "/user/login",
                    "/user/sendMsg"
            };
            boolean check = check(urls, requestURI);
            //遍历到有不需要处理的请求路径
            if(check){
                filterChain.doFilter(request, response);
                return;
            }
            //需要拦截,返回给前端拦截器进行页面跳转
            response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        }

    }

    /**
     * 路径匹配，定义的请求路径与拦截的路径进行匹配
     * ture为不需要拦截
     * @param urls
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls,String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match){
                return true;
            }
        }
        return false;
    }
}
