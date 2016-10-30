package com.github.vkuzel.orm_frameworks_demo.activejpa.config;

import org.activejpa.jpa.JPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
public class PersistenceConfiguration {

    private static DataSource dataSourceStatic;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @PostConstruct
    private void init() {
        dataSourceStatic = dataSource;
        JPA.instance.addPersistenceUnit("default", entityManagerFactory, true);
    }

    // An odd way to propagate DataSource to SeatsLayoutConverter so it can be
    // used to create an array.
    public static DataSource getDataSourceStatic() {
        if (dataSourceStatic == null) {
            throw new IllegalStateException("Data source is not initialised! You have to wait for bean to construct!");
        }
        return dataSourceStatic;
    }
}
