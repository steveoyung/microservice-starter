package com.yonyou.microservice.filter.config;


import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * mybatis 配置数据源类
 * 
 * @author joy
 * @date 2016年12月15日
 * @since 1.7
 */
@Configuration
@EnableTransactionManagement
public class MybatisConfiguration implements EnvironmentAware {


    private RelaxedPropertyResolver propertyResolver;

    private String driveClassName;
    private String url;
    private String userName;
    private String password;
    private String xmlLocation;
    private String typeAliasesPackage;
    private String filters;
    private String maxActive;
    private String initialSize;
    private String maxWait;
    private String minIdle;
    private String timeBetweenEvictionRunsMillis;
    private String minEvictableIdleTimeMillis;
    private String validationQuery;
    private String testWhileIdle;
    private String testOnBorrow;
    private String testOnReturn;
    private String poolPreparedStatements;
    private String maxOpenPreparedStatements;
    private DruidDataSource druidDataSource;

    //  @Bean("groovyDataSource")
    /**
     * 
     * 数据源配置
     * 
     */
    public DataSource druidDataSource() {
    	if(this.druidDataSource!=null){
    		return this.druidDataSource;
    	}
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(userName);
        druidDataSource.setPassword(password);
        druidDataSource.setDriverClassName(StringUtils.isNotBlank(driveClassName)?driveClassName:"com.mysql.jdbc.Driver");
        druidDataSource.setMaxActive(StringUtils.isNotBlank(maxActive)? Integer.parseInt(maxActive):10);
        druidDataSource.setInitialSize(StringUtils.isNotBlank(initialSize)? Integer.parseInt(initialSize):1);
        druidDataSource.setMaxWait(StringUtils.isNotBlank(maxWait)? Integer.parseInt(maxWait):60000);
        druidDataSource.setMinIdle(StringUtils.isNotBlank(minIdle)? Integer.parseInt(minIdle):3);
        druidDataSource.setTimeBetweenEvictionRunsMillis(StringUtils.isNotBlank(timeBetweenEvictionRunsMillis)?
                Integer.parseInt(timeBetweenEvictionRunsMillis):60000);
        druidDataSource.setMinEvictableIdleTimeMillis(StringUtils.isNotBlank(minEvictableIdleTimeMillis)?
                Integer.parseInt(minEvictableIdleTimeMillis):300000);
        druidDataSource.setValidationQuery(StringUtils.isNotBlank(validationQuery)?validationQuery:"select 'x'");
        druidDataSource.setTestWhileIdle(StringUtils.isNotBlank(testWhileIdle)? Boolean.parseBoolean(testWhileIdle):true);
        druidDataSource.setTestOnBorrow(StringUtils.isNotBlank(testOnBorrow)? Boolean.parseBoolean(testOnBorrow):false);
        druidDataSource.setTestOnReturn(StringUtils.isNotBlank(testOnReturn)? Boolean.parseBoolean(testOnReturn):false);
        druidDataSource.setPoolPreparedStatements(StringUtils.isNotBlank(poolPreparedStatements)? Boolean.parseBoolean(poolPreparedStatements):true);
        druidDataSource.setMaxOpenPreparedStatements(StringUtils.isNotBlank(maxOpenPreparedStatements)? Integer.parseInt(maxOpenPreparedStatements):20);

        try {
            druidDataSource.setFilters(StringUtils.isNotBlank(filters)?filters:"stat, wall");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.druidDataSource=druidDataSource;
        return druidDataSource;
    }

    @Bean(name = "groovySqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(druidDataSource());
        if(StringUtils.isNotBlank(typeAliasesPackage)){
            bean.setTypeAliasesPackage(typeAliasesPackage);
        }
        //分页插件
//        PageHelper pageHelper = new PageHelper();
//        Properties properties = new Properties();
//        properties.setProperty("reasonable", "true");
//        properties.setProperty("supportMethodsArguments", "true");
//        properties.setProperty("returnPageInfo", "check");
//        properties.setProperty("params", "count=countSql");
//        pageHelper.setProperties(properties);
        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        Interceptor[] plugins =  new Interceptor[]{pageHelper};
//        bean.setPlugins(plugins);
        try {
            bean.setMapperLocations(resolver.getResources(xmlLocation));
            return bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(Environment environment){

        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("groovySqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.yonyou.microservice.filter");
        return mapperScannerConfigurer;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment, null);
        this.url = propertyResolver.getProperty("groovy.db.url");
        this.userName= propertyResolver.getProperty("groovy.db.username");
        this.password = propertyResolver.getProperty("groovy.db.password");
        this.driveClassName = propertyResolver.getProperty("groovy.db.driver-class-name");
        this.filters = propertyResolver.getProperty("spring.datasource.filters");
        this.maxActive = propertyResolver.getProperty("spring.datasource.maxActive");
        this.initialSize = propertyResolver.getProperty("spring.datasource.initialSize");
        this.maxWait = propertyResolver.getProperty("spring.datasource.maxWait");
        this.minIdle = propertyResolver.getProperty("spring.datasource.minIdle");
        this.timeBetweenEvictionRunsMillis = propertyResolver.getProperty("spring.datasource.timeBetweenEvictionRunsMillis");
        this.minEvictableIdleTimeMillis = propertyResolver.getProperty("spring.datasource.minEvictableIdleTimeMillis");
        this.validationQuery = propertyResolver.getProperty("spring.datasource.validationQuery");
        this.testWhileIdle = propertyResolver.getProperty("spring.datasource.testWhileIdle");
        this.testOnBorrow = propertyResolver.getProperty("spring.datasource.testOnBorrow");
        this.testOnReturn = propertyResolver.getProperty("spring.datasource.testOnReturn");
        this.poolPreparedStatements = propertyResolver.getProperty("spring.datasource.poolPreparedStatements");
        this.maxOpenPreparedStatements = propertyResolver.getProperty("spring.datasource.maxOpenPreparedStatements");
        this.typeAliasesPackage = propertyResolver.getProperty("groovy.db.typeAliasesPackage");
        this.xmlLocation = propertyResolver.getProperty("groovy.db.xmlLocation");
    }
    /**
     * 
     * 事务配置
     * 
     */
    @Bean("groovyTrans")
    public DataSourceTransactionManager transactionManager2() {
        return new DataSourceTransactionManager(druidDataSource());
    }

}
