package com.github.vkuzel.orm_frameworks_demo.initializer;

import com.github.vkuzel.orm_frameworks_demo.common.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.SQLException;

@Component
/*
  Spring Boot's DataSourceInitializer can't handle PostgreSQL's non-standard
  syntax so I had to implement my own.
 */
public class DatabaseInitializer {

    private static final PathMatchingResourcePatternResolver RESOURCE_RESOLVER = new PathMatchingResourcePatternResolver();

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    private void initSchema() {
        executeScript("classpath:schema.sql");
        executeScript("classpath:data.sql");
    }

    private void executeScript(String location) {
        Resource data = RESOURCE_RESOLVER.getResource(location);
        String sql = Utils.loadResourceToString(data);
        jdbcTemplate.execute(sql);
        try {
            jdbcTemplate.getDataSource().getConnection().commit();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
