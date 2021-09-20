package com.eleven.crm.workbench.service.impl;

import com.eleven.crm.utils.MybatisUtils;
import com.eleven.crm.vo.PaginationVo;
import com.eleven.crm.workbench.dao.ActivityDao;
import com.eleven.crm.workbench.dao.ActivityRemarkDao;
import com.eleven.crm.workbench.domain.Activity;
import com.eleven.crm.workbench.service.ActivityService;

import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {
    ActivityDao activityDao = MybatisUtils.getSqlSession().getMapper(ActivityDao.class);
    ActivityRemarkDao remarkDao = MybatisUtils.getSqlSession().getMapper(ActivityRemarkDao.class);
    @Override
    public boolean saveActivity(Activity activity) {
        boolean flag = true;
        int count = activityDao.saveActivity(activity);
        if (count!=1){
            flag = false;
        }
        return flag;
    }

    @Override
    public PaginationVo<Activity> pageList(Map<String, Object> map) {
        //有条件的记录条数
        int total = activityDao.getTotalByCondition(map);
        //有条件的dataList
        List<Activity> activities = activityDao.getActivityByCondition(map);
        //创建Vo封装记录和数据
        PaginationVo<Activity> vo = new PaginationVo<>();
        vo.setTotal(total);
        vo.setDataList(activities);
        return vo;
    }

    @Override
    public boolean deleteActivity(String[] ids) {
        boolean flag = true;
        //查询需要删除的备注数量
        int count1 = remarkDao.getCountByAids(ids);
        //删除备注,返回受到影响的数量
        int count2 = remarkDao.deleteByAids(ids);
        if(count1!=count2){
            flag = false;
        }
        //删除市场活动
        int count3 = activityDao.delete(ids);
        if (count3!=ids.length){
            flag = false;
        }
        return flag;
    }


}
