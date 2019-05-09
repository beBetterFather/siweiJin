package com.app.modules.plugins;

import com.alibaba.druid.pool.DruidPooledPreparedStatement;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.jdbc.Null;
import org.apache.ibatis.logging.jdbc.PreparedStatementLogger;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;
import java.util.Properties;

@Intercepts(
        {@Signature(type= StatementHandler.class, method="query", args={Statement.class, ResultHandler.class})}
)
public class MyBatisPlugin implements Interceptor {

    private static final Logger LOG = LoggerFactory.getLogger(MyBatisPlugin.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        LOG.info("plugin begin:");
        //获取参数
        Object[] objects = invocation.getArgs();
        //显示第一个参数
        DruidPooledPreparedStatement statement = (DruidPooledPreparedStatement) objects[0];
        LOG.info("sql is = {}", statement.getSql());
        Object object = invocation.proceed();
        LOG.info("plugin end。");
        return object;
    }

    /**
     * 包装目标对象，为目标对象创建一个代理对象
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        Object proxy = Plugin.wrap(target, this);
        return proxy;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
