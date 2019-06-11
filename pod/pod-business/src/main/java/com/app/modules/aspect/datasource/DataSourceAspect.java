package com.app.modules.aspect.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 *  读写分离多数据源选择
 */
@Aspect
@Component
public class DataSourceAspect {

    private static final Logger LOG = LoggerFactory.getLogger(DataSourceAspect.class);

    @Pointcut("execution(* com.app.modules.service..*.*(..))")
    public void dataSourceAop(){}

    /**
     * 拦截service implement方法
     */
    @Before(value = "dataSourceAop()")
    public void before(JoinPoint point){
        //Object getTarget()：返回被织入advice的目标对象
        Object target = point.getTarget();
        //Signature getSignature()：返回被增强的方法的相关信息。
        Signature signature = point.getSignature();
        //获取拦截对象的方法
        String methodName = signature.getName();

        //获取目标对象继承的接口
        Class<?>[] classz  = target.getClass().getInterfaces();
        //获取目标方法的参数类型
        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();
        try {
            Method method = classz[0].getMethod(methodName, parameterTypes);
            if(method!=null && method.isAnnotationPresent(SelectDataSource.class)){
                //在这边注入数据源信息
                SelectDataSource data = method.getAnnotation(SelectDataSource.class);
                LOG.info("用户选择的数据库类型是: {}", data.value());
                HandleDataSource.putDataSource(data.value());
            }
        } catch (NoSuchMethodException e) {
            LOG.error("获取用户数据库类型失败: {}", e);
        }
    }

}
