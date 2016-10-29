package com.github.vkuzel.orm_frameworks_demo.activejdbc.config;

import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;

@Configuration
public class PersistenceConfiguration {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void openConnection() {
        Base.open(dataSource);
    }

// This won't work because @PreDestrory methods are called from a different
// thread than @PostConstruct. ActiveJDBC attaches a connection to the thread.
// In real world MVC application you would like to open connection on every
// HTTP request by implementing some kind of request handler.
//
//    @PreDestroy
//    public void closeConnection() {
//        Base.close();
//    }
}
