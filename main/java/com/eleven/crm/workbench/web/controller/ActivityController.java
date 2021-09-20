package com.eleven.crm.workbench.web.controller;

import com.eleven.crm.settings.domain.User;
import com.eleven.crm.settings.service.UserService;
import com.eleven.crm.settings.service.impl.UserServiceImpl;
import com.eleven.crm.utils.DateTimeUtil;
import com.eleven.crm.utils.MybatisUtils;
import com.eleven.crm.utils.PrintJson;
import com.eleven.crm.utils.UUIDUtil;
import com.eleven.crm.vo.PaginationVo;
import com.eleven.crm.workbench.domain.Activity;
import com.eleven.crm.workbench.service.ActivityService;
import com.eleven.crm.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("市场活动控制器..");
        String path = req.getServletPath();
        System.out.println(path);
        if("/workbench/activity/getUserList.do".equals(path)){
            //ccc(req,resp);
            getUserList(req,resp);
        }else if("/workbench/activity/save.do".equals(path)){
            saveActivity(req,resp);
        }else if("/workbench/activity/pageList.do".equals(path)){
            pageList(req,resp);
        }else if("/workbench/activity/delete.do".equals(path)){
            deleteActivity(req,resp);
        }
    }

    private void deleteActivity(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("删除市场活动操作!!");
        String[] ids = req.getParameterValues("id");
        ActivityService service = (ActivityService) MybatisUtils.getService(new ActivityServiceImpl());
        boolean flag = service.deleteActivity(ids);
        PrintJson.printJsonFlag(resp,flag);
    }

    private void pageList(HttpServletRequest req, HttpServletResponse resp) {

        String  pageNoStr = req.getParameter("pageNo");
        String  pageSizeStr = req.getParameter("pageSize");
        String  name = req.getParameter("name");
        String  owner = req.getParameter("owner");
        String  startDate = req.getParameter("startDate");
        String  endDate = req.getParameter("endDate");
        int pageNo = Integer.valueOf(pageNoStr);
        int pageSize = Integer.valueOf(pageSizeStr);
        //略过的记录数
        int skipCount = (pageNo-1)*pageSize;
        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);
        ActivityService service = (ActivityService) MybatisUtils.getService(new ActivityServiceImpl());
        PaginationVo<Activity> vo  = service.pageList(map);
        PrintJson.printJsonObj(resp,vo);

    }

    private void saveActivity(HttpServletRequest req, HttpServletResponse resp) {
        //添加操作
        //获取表单的信息
        String id = UUIDUtil.getUUID();
        String owner = req.getParameter("owner");
        String name = req.getParameter("name");
        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");
        String cost = req.getParameter("cost");
        String description = req.getParameter("description");
        //当前系统时间
        String createTime = DateTimeUtil.getSysTime();
        //创建人从session作用域中拿出来
        String createBy = ((User) req.getSession().getAttribute("user")).getName();
        //放入Activity对象中
        Activity activity = new Activity();
        activity.setId(id);
        activity.setName(name);
        activity.setCost(cost);
        activity.setCreateBy(createBy);
        activity.setCreateTime(createTime);
        activity.setOwner(owner);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setDescription(description);
        //代理
        ActivityService service = (ActivityService) MybatisUtils.getService(new ActivityServiceImpl());
        boolean flag = service.saveActivity(activity);
        PrintJson.printJsonFlag(resp,flag);

    }

    private void getUserList(HttpServletRequest req, HttpServletResponse resp) {
        //取得用户信息列表
        UserService service = (UserService) MybatisUtils.getService(new UserServiceImpl());
        List<User> users = service.getUserList();
        PrintJson.printJsonObj(resp,users);

    }


}
