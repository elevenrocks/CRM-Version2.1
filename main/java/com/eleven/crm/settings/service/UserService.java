package com.eleven.crm.settings.service;

import com.eleven.crm.exception.LoginException;
import com.eleven.crm.settings.domain.User;

import java.util.List;

public interface UserService {

    User login(String loginAct, String loginPwd, String ip) throws LoginException;

    List<User> getUserList();
}
