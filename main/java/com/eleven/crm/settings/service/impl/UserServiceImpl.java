package com.eleven.crm.settings.service.impl;

import com.eleven.crm.exception.LoginException;
import com.eleven.crm.settings.dao.UserDao;
import com.eleven.crm.settings.domain.User;
import com.eleven.crm.settings.service.UserService;
import com.eleven.crm.utils.DateTimeUtil;
import com.eleven.crm.utils.MybatisUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao userDao = MybatisUtils.getSqlSession().getMapper(UserDao.class);

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {
        Map<String,String> map = new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        User user = userDao.login(map);

        if(user==null){
            throw new LoginException("账号密码错误");
        }

        //如果验证成功,继续验证
        String expireTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();
        if(expireTime.compareTo(currentTime)<0){
            throw new LoginException("账号已失效");
        }
        //判断状态
        String lockState = user.getLockState();
        if ("0".equals(lockState)){
            throw  new LoginException("账号已经锁定");
        }
        //判断
        String allowIps = user.getAllowIps();
        if(!allowIps.contains(ip)){
            throw new LoginException("ip地址受限制");
        }
        return user;
    }

    @Override
    public List<User> getUserList() {
        return userDao.getUserList();

    }
}
