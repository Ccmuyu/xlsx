package com.zzw.xlsx.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    private static Logger log = LoggerFactory.getLogger(DataSourceConfig.class);
    @Bean
    @ConfigurationProperties(prefix = "spring.local.datasource")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.test.datasource")
    public DataSource dataSourceTest() {
        return new DruidDataSource();
    }

    @Bean
    @Primary
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
        List<Map<String, Object>> tables = jdbcTemplate.queryForList("show tables");
        return jdbcTemplate;
    }

    @Bean
    public JdbcTemplate jdbcTemplateTest() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceTest());
        List<Map<String, Object>> tables = jdbcTemplate.queryForList("show tables");
        return jdbcTemplate;
    }
}
