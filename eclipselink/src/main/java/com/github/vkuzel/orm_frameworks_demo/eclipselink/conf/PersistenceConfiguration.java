package com.github.vkuzel.orm_frameworks_demo.eclipselink.conf;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Map;

@Configuration
@AutoConfigureAfter({DataSourceAutoConfiguration.class})
@EnableTransactionManagement
public class PersistenceConfiguration extends JpaBaseConfiguration {

    private static DataSource dataSource;

    protected PersistenceConfiguration(DataSource dataSource, JpaProperties properties, ObjectProvider<JtaTransactionManager> jtaTransactionManagerProvider) {
        super(dataSource, properties, jtaTransactionManagerProvider);
        PersistenceConfiguration.dataSource = dataSource;
    }

    @Override
    protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
        return new EclipseLinkJpaVendorAdapter();
    }

    @Override
    protected Map<String, Object> getVendorProperties() {
        return Collections.emptyMap();
    }

    // An odd way to propagate DataSource to SeatsLayoutConverter so it can be
    // used to create an array.
    public static DataSource getDataSourceStatic() {
        if (dataSource == null) {
            throw new IllegalStateException("Data source is not initialised! You have to wait for bean to construct!");
        }
        return dataSource;
    }
}
