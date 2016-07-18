package com.github.vkuzel.orm_frameworks_demo.mybatis.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

// MyBatis Spring Boot integration scans for mapper interfaces annotated with
// @Mapper annotation. But @Mapper annotation is not added by code generator
// by default so that's why I decided to not use it.
@Configuration
@MapperScan("com.github.vkuzel.orm_frameworks_demo.mybatis.dao")
public class PersistenceConfiguration {
}
