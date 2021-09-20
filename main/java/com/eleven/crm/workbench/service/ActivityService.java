package com.eleven.crm.workbench.service;

import com.eleven.crm.vo.PaginationVo;
import com.eleven.crm.workbench.domain.Activity;

import java.util.Map;

public interface ActivityService {
    boolean saveActivity(Activity activity);

    PaginationVo<Activity> pageList(Map<String, Object> map);

    boolean deleteActivity(String[] ids);
}
