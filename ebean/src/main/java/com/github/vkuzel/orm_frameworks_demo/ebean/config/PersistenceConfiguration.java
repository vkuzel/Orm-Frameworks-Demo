package com.github.vkuzel.orm_frameworks_demo.ebean.config;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.springsupport.txn.SpringAwareJdbcTransactionManager;
import com.github.vkuzel.orm_frameworks_demo.ebean.type.PlaneDimensionsScalarType;
import com.github.vkuzel.orm_frameworks_demo.ebean.type.PlaneTypeScalarType;
import com.github.vkuzel.orm_frameworks_demo.service.UsernameProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

import javax.persistence.Entity;
import javax.sql.DataSource;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class PersistenceConfiguration {

    private static final ClassLoader CLASS_LOADER = PersistenceConfiguration.class.getClassLoader();

    private static final Function<BeanDefinition, Class> TO_ENTITY_CLASS = beanDefinition -> {
        try {
            return ClassUtils.forName(beanDefinition.getBeanClassName(), CLASS_LOADER);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    };

    @Bean
    public EbeanServer ebeanServer(DataSource dataSource) {
        ServerConfig config = new ServerConfig();
        config.setName("orm-frameworks-demo");
        config.setCurrentUserProvider(UsernameProvider::getUsername);

        config.addClass(PlaneDimensionsScalarType.class);
        config.addClass(PlaneTypeScalarType.class);

        findEntityClasses("com.github.vkuzel.orm_frameworks_demo.ebean.domain")
                .forEach(config::addClass);

        config.setDataSource(dataSource);
        config.setExternalTransactionManager(new SpringAwareJdbcTransactionManager());

        config.setDefaultServer(true);
        config.setRegister(true);

        return EbeanServerFactory.create(config);
    }

    private List<Class> findEntityClasses(String packageName) {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Entity.class));
        Set<BeanDefinition> entityDefinitions = scanner.findCandidateComponents(packageName);
        return entityDefinitions.stream().map(TO_ENTITY_CLASS).collect(Collectors.toList());
    }
}
