package com.yabushan.framework.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.Properties;

@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
@Slf4j
@Component
public class MySqlInterceptor implements Interceptor {


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, new DefaultObjectFactory(), new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());
        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
        BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");

        //这里为sql的处理逻辑，将sql中的test1_替换为test2_
        String newSql = boundSql.getSql().replaceAll("(?i)test1_", "test2_");

        if (newSql != null) {
            log.debug("替换后SQL =====>" + newSql);
            metaStatementHandler.setValue("delegate.boundSql.sql", newSql);
        }

        // 传递给下一个拦截器处理
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);

        // 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的次数
//        if (target instanceof StatementHandler) {
//            return Plugin.wrap(target, this);
//        } else {
//            return target;
//        }
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
