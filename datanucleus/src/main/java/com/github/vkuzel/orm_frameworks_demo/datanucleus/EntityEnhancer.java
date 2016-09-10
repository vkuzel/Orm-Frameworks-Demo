package com.github.vkuzel.orm_frameworks_demo.datanucleus;

import org.apache.commons.lang3.Validate;
import org.datanucleus.enhancer.DataNucleusEnhancer;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import javax.persistence.Entity;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EntityEnhancer {

    private static final ClassPathScanningCandidateComponentProvider ENTITY_SCANNER;
    static {
        ENTITY_SCANNER = new ClassPathScanningCandidateComponentProvider(false);
        ENTITY_SCANNER.addIncludeFilter(new AnnotationTypeFilter(Entity.class));
    }

    public static void main(String[] args) throws IOException {
        Validate.isTrue(args.length == 1, "Expected single argument <package_to_scan>!");
        String pathToScan = args[0];
        String[] classesToEnhance = findEntityClasses(pathToScan);
        Validate.isTrue(classesToEnhance.length > 0, "No classes to enhance has been found!");

        DataNucleusEnhancer enhancer = new DataNucleusEnhancer("JPA", null);
        enhancer.addClasses(classesToEnhance);
        enhancer.enhance();
    }

    private static String[] findEntityClasses(String packageToScan) throws IOException {
        Set<BeanDefinition> entityBeanDefinitions = ENTITY_SCANNER.findCandidateComponents(packageToScan);
        List<String> entityClasses = entityBeanDefinitions.stream().map(BeanDefinition::getBeanClassName).collect(Collectors.toList());
       return entityClasses.toArray(new String[]{});
    }
}
