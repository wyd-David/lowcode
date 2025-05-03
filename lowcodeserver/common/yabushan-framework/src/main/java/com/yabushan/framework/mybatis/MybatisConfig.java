package com.yabushan.framework.mybatis;


import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @Author yabushan
 * @Date 2021/1/16 18:16
 * @Version 1.0
 */

@Configuration
@ConditionalOnBean({SqlSessionFactory.class})
public class MybatisConfig {
    @Bean
    public String myInterceptor(SqlSessionFactory sqlSessionFactory) {
        sqlSessionFactory.getConfiguration().addInterceptor(new MySqlInterceptor());
        return "myInterceptor";
    }
}
