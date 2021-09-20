package com.eleven.crm.settings.web.controller;

import com.eleven.crm.exception.LoginException;
import com.eleven.crm.settings.domain.User;
import com.eleven.crm.settings.service.UserService;
import com.eleven.crm.settings.service.impl.UserServiceImpl;
import com.eleven.crm.utils.MD5Util;
import com.eleven.crm.utils.MybatisUtils;
import com.eleven.crm.utils.PrintJson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("test...");
        String path = req.getServletPath();
        System.out.println(path);
        if("/settings/user/login.do".equals(path)){
            login(req,resp);
        }else if("/settings/user/xxx.do".equals(path)){

        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) {
        //接收前端参数
        String loginAct = req.getParameter("loginAct");
        String loginPwd = req.getParameter("loginPwd");
        //密文
        loginPwd = MD5Util.getMD5(loginPwd);
        //ip
        String ip = req.getRemoteAddr();
        System.out.println("--------->"+ip);

        //动态代理
        UserService service = (UserService) MybatisUtils.getService(new UserServiceImpl());
        System.out.println(service.getClass().getName());

        try {
            User user = service.login(loginAct,loginPwd,ip);
            //
            req.getSession().setAttribute("user",user);
            PrintJson.printJsonFlag(resp,true);
        } catch (Exception e) {
            //获取异常信息
            String msg = e.getMessage();
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("success",false);
            map.put("msg",msg);
            //异常
            PrintJson.printJsonObj(resp,map);
        }

    }

}
