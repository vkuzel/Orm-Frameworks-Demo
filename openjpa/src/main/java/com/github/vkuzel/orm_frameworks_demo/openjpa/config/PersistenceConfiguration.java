package com.github.vkuzel.orm_frameworks_demo.openjpa.config;

import com.github.vkuzel.orm_frameworks_demo.openjpa.domain.SerializableJson;
import com.github.vkuzel.orm_frameworks_demo.openjpa.mapping.*;
import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneDimensions;
import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneType;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.OpenJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@EnableTransactionManagement
public class PersistenceConfiguration extends JpaBaseConfiguration {

    public PersistenceConfiguration(DataSource dataSource, JpaProperties properties, ObjectProvider<JtaTransactionManager> jtaTransactionManager, ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
        super(dataSource, properties, jtaTransactionManager, transactionManagerCustomizers);
    }

    private static final Function<Map.Entry<Class<? extends Serializable>, Class<? extends Serializable>>, String> TO_CONFIGURATION_VALUE = e ->
            e.getKey().getName() + "=" + e.getValue().getName();

    @Override
    protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
        return new OpenJpaVendorAdapter();
    }

    @Override
    protected Map<String, Object> getVendorProperties() {
        return ImmutableMap.of(
                // http://openjpa.apache.org/builds/2.4.1/apache-openjpa/docs/ref_guide_dbsetup_thirdparty.html
                "openjpa.ConnectionFactoryMode", "managed",
                // It is possible to annotate each field by @Strategy
                // annotation or define global field strategies here. I decided
                // to use second approach which seems more clear to me.
                "openjpa.jdbc.MappingDefaults", "FieldStrategies='" + getFieldStrategies() + "'",
                "openjpa.Auditor", "com.github.vkuzel.orm_frameworks_demo.openjpa.audit.EntityAuditor"
        );
    }

    private String getFieldStrategies() {
        return ImmutableMap.of(
                LocalDateTime.class, LocalDateTimeValueHandler.class,
                DetailPlaneDimensions.class, PlaneDimensionsValueHandler.class,
                DetailPlaneType.class, PlaneTypeValueHandler.class,
                Integer[].class, IntArrayFieldStrategy.class,
                SerializableJson.class, SerializableJsonValueHandler.class
        ).entrySet().stream().map(TO_CONFIGURATION_VALUE).collect(Collectors.joining(","));
    }
}
