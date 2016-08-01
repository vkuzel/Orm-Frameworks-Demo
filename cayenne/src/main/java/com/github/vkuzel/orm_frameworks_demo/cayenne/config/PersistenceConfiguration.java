package com.github.vkuzel.orm_frameworks_demo.cayenne.config;

import com.github.vkuzel.orm_frameworks_demo.cayenne.audit.AuditingListener;
import com.github.vkuzel.orm_frameworks_demo.cayenne.extendedtype.*;
import org.apache.cayenne.access.DataDomain;
import org.apache.cayenne.access.DataNode;
import org.apache.cayenne.access.types.ExtendedTypeMap;
import org.apache.cayenne.configuration.DataNodeDescriptor;
import org.apache.cayenne.configuration.server.DataSourceFactory;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.commons.lang.Validate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class PersistenceConfiguration implements ApplicationContextAware {

    @Bean
    public ServerRuntime serverRuntime() {
        ServerRuntime serverRuntime = new ServerRuntime("cayenne-demo.xml");

        DataDomain dataDomain = serverRuntime.getDataDomain();
        dataDomain.addListener(new AuditingListener());

        DataNode dataNode = dataDomain.getDataNode("ormframeworksdemo");
        ExtendedTypeMap extendedTypes = dataNode.getAdapter().getExtendedTypes();
        extendedTypes.registerType(new JsonNodeExtendedType());
        extendedTypes.registerType(new LocalDateTimeExtendedType());
        extendedTypes.registerType(new PlaneDimensionsExtendedType());
        extendedTypes.registerType(new PlaneTypeExtendedType());
        extendedTypes.registerType(new IntegerArrayExtendedType());

        return serverRuntime;
    }

    private static ApplicationContext appContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appContext = applicationContext;
    }

    public static class CayenneDataSourceFactory implements DataSourceFactory {

        @Override
        public DataSource getDataSource(DataNodeDescriptor nodeDescriptor) throws Exception {
            ApplicationContext springApplicationContext = appContext;
            Validate.notNull(springApplicationContext);
            return springApplicationContext.getBean(DataSource.class);
        }
    }
}
