package com.eleven.crm.testcrm01;


import com.eleven.crm.settings.dao.UserDao;
import com.eleven.crm.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class Crm01 {
    @Test
    public void testCrm01() {
        SqlSession sqlSession= MybatisUtils.getSqlSession();
        UserDao dao = sqlSession.getMapper(UserDao.class);
        System.out.println(dao);

    }
}
