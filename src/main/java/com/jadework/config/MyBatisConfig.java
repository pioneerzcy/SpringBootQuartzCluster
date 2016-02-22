package com.jadework.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @Copyright: Copyright (c) 2016,${year},
 * @Description: ${todo}
 */


@Configuration
public class MyBatisConfig {

    public  interface BeanNames{
        String SQL_SESSION="SQL_SESSION";
    }

    @Primary
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "task.server.datasource")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }


    /**
     * 通用sqlSessionFactory
     */
    @Bean(name = "sqlSessionFactory")
    protected SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mybatis/mapper/*.xml"));
        sqlSessionFactoryBean.setConfigLocation(resolver.getResource("classpath:mybatis/mybatis-config.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 普通sqlSession
     * @return
     * @throws Exception
     */
    @Bean(name =BeanNames.SQL_SESSION)
    public SqlSessionTemplate SqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactoryBean());
    }
}
