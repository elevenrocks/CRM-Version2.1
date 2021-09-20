package com.eleven.crm.settings.web.fillter;

import com.eleven.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("没有登录过的用户...");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse ;
        //
        String  path = request.getServletPath();
        //不应该被拦截的资源自动放行
        if("/login.jsp".equals(path)||"/settings/user/login.do".equals(path)){
            filterChain.doFilter(request,response);
        }else {
            User user = (User) request.getSession().getAttribute("user");
            if (user != null) {
                //放行
                filterChain.doFilter(request, response);
            } else {
                //重定向到login.jsp
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
        }
    }

    @Override
    public void destroy() {

    }
}
