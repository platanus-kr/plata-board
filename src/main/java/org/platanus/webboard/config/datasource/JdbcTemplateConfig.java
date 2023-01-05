package org.platanus.webboard.config.datasource;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * DataSource 에 JDBCTemplate 를 주입하기 위한 설정
 */
//@Configuration
public class JdbcTemplateConfig {
    //    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
