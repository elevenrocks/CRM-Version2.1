package com.eleven.crm.workbench.dao;

import com.eleven.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityDao {

    int saveActivity(Activity activity);


    List<Activity> getActivityByCondition(Map<String, Object> map);

    int getTotalByCondition(Map<String, Object> map);

    int delete(String[] ids);
}
