package com.app.modules.config;

/**
 * 线程选择数据源处理工具类
 */
public class HandleDataSource {

    public static ThreadLocal<String> holder = new ThreadLocal<String>();

    //设置当前线程的数据源
    public static void putDataSource(String dataSource){
        holder.set(dataSource);
    }

    //获取当前线程的数据源
    public static String getDataSource(){
        return holder.get();
    }
}
