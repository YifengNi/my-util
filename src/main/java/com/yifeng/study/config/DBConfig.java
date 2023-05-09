package com.yifeng.study.config;

import com.baomidou.mybatisplus.MybatisConfiguration;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import com.github.pagehelper.PageInterceptor;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

@Data
@Configuration
// 前缀为primary.datasource.druid的配置信息
@MapperScan(basePackages = "com.yifeng.study.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
public class DBConfig {

    @Autowired
    private Environment env;

    private static final String MAPPER_LOCATION = "classpath:mapper/*.xml";

    /**
     * 分页插件
     *
     * @return PaginationInterceptor
     */
    @Primary
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setLocalPage(true);
        return paginationInterceptor;
    }

    @Bean
    public PageInterceptor pageInterceptor() {
        return new PageInterceptor();
    }

    // 数据源
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSource.setJdbcUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        if (StringUtils.isNotEmpty(env.getProperty("spring.datasource.maxPoolSize"))){
            dataSource.setMaximumPoolSize(Integer.parseInt(env.getProperty("spring.datasource.maxPoolSize")));
        }
        // # 字符编码，支持emoji
        if (!StringUtils.isEmpty(env.getProperty("spring.datasource.hikari.connection-init-sql"))){
            dataSource.setConnectionInitSql(env.getProperty("spring.datasource.hikari.connection-init-sql"));
        }
        return dataSource;
    }

    // 创建该数据源的事务管理
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager() throws SQLException {
        return new DataSourceTransactionManager(dataSource());
    }


    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory carSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource
            , @Qualifier("paginationInterceptor") PaginationInterceptor paginationInterceptor
            , PageInterceptor pageInterceptor) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(true);
        sqlSessionFactory.setConfiguration(configuration);
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(MAPPER_LOCATION));
        //添加分页功能
        sqlSessionFactory.setPlugins(new org.apache.ibatis.plugin.Interceptor[]{paginationInterceptor, pageInterceptor});
        return sqlSessionFactory.getObject();
    }

}
