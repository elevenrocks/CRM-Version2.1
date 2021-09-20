package com.eleven.crm.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MybatisUtils {
    public static SqlSessionFactory factory = null;
    static {
        String config = "mybatis.xml";
        InputStream in = null;
        try {
            in = Resources.getResourceAsStream(config);
            factory = new SqlSessionFactoryBuilder().build(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static ThreadLocal<SqlSession> t = new ThreadLocal<SqlSession>();

    public static SqlSession getSqlSession(){

        SqlSession session = t.get();

        if(session==null){

            session = factory.openSession();
            t.set(session);
        }

        return session;

    }

    public static void myClose(SqlSession session){

        if(session!=null){
            session.close();
            t.remove();
        }

    }
    public static Object getService(Object service){

        return new TransactionInvocationHandler(service).getProxy();

    }

}
