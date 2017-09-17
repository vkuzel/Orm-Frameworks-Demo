package com.github.vkuzel.orm_frameworks_demo.datanucleus.config;

import com.google.common.collect.ImmutableMap;
import org.datanucleus.PropertyNames;
import org.datanucleus.store.rdbms.adapter.PostgreSQLAdapter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@AutoConfigureAfter({ DataSourceAutoConfiguration.class })
@EnableTransactionManagement
public class PersistenceConfiguration extends JpaBaseConfiguration {

    public PersistenceConfiguration(DataSource dataSource, JpaProperties properties, ObjectProvider<JtaTransactionManager> jtaTransactionManager, ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
        super(dataSource, properties, jtaTransactionManager, transactionManagerCustomizers);
    }

    @Override
    protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
        return new DataNucleusVendorAdapter();
    }

    @Override
    protected Map<String, Object> getVendorProperties() {
        return ImmutableMap.of(
                "datanucleus.rdbms.datastoreAdapterClassName", PostgreSQLAdapter.class.getName(),
                // Note: Identifier factory is going to be replaced by
                // identifier naming factory in future.
                // @see PropertyNames.PROPERTY_IDENTIFIER_NAMING_FACTORY
                PropertyNames.PROPERTY_IDENTIFIER_FACTORY, "underscore",
                PropertyNames.PROPERTY_IDENTIFIER_CASE, "lowercase"
        );
    }
}
