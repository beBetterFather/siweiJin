package com.app.modules.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.app.modules.aspect.datasource.DynamicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {

//    @ConfigurationProperties(prefix = "master.datasource")
    public DataSource masterDataSource() throws SQLException {
        DruidDataSource master = new DruidDataSource();
        master.setDbType("com.alibaba.druid.pool.DruidDataSource");
        master.setDriver(new com.mysql.jdbc.Driver());
        master.setUrl("jdbc:mysql://118.31.15.106:3306/demo?useSSL=false&useUnicode=true&characterEncoding=UTF-8");
        master.setUsername("root");
        master.setPassword("admin");
        master.setInitialSize(6);
        return master;
    }
//    @ConfigurationProperties(prefix = "slaver.datasource")
    public DataSource slaverDataSource() throws SQLException {
        DruidDataSource slaver = new DruidDataSource();
        slaver.setDbType("com.alibaba.druid.pool.DruidDataSource");
        slaver.setDriver(new com.mysql.jdbc.Driver());
        slaver.setUrl("jdbc:mysql://118.31.15.106:3306/slaver_demo?useSSL=false&useUnicode=true&characterEncoding=UTF-8");
        slaver.setUsername("root");
        slaver.setPassword("admin");
        slaver.setInitialSize(6);
        return  slaver;
    }

    @Bean(name = "dataSource")
    public DataSource dynamicDataSource() throws SQLException {
        DynamicDataSource dataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("slaver", slaverDataSource());
        targetDataSources.put("master", masterDataSource());
        dataSource.setTargetDataSources(targetDataSources);
        dataSource.setDefaultTargetDataSource(masterDataSource());
        return dataSource;
    }


    //配置一个管理后台的servlet
    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new StatViewServlet());
        servletRegistrationBean.addUrlMappings("/druid/*");
        servletRegistrationBean.addInitParameter("loginUsername", "pod");
        servletRegistrationBean.addInitParameter("loginPassword", "123");
        return servletRegistrationBean;
    }

    //配置一个web监控的filter
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js ,*.gif ,*.jpg ,*.png ,*.css ,*.ico ,/druid/*");
        return filterRegistrationBean;
    }
}
