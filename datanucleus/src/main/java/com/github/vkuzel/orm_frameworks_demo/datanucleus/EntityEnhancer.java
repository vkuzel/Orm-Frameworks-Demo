package com.github.vkuzel.orm_frameworks_demo.datanucleus;

import org.apache.commons.lang3.Validate;
import org.datanucleus.enhancer.DataNucleusEnhancer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import javax.persistence.Entity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EntityEnhancer {

    private static final PathMatchingResourcePatternResolver RESOURCE_RESOLVER = new PathMatchingResourcePatternResolver();

    private static final MetadataReaderFactory METADATA_READER_FACTORY = new CachingMetadataReaderFactory(RESOURCE_RESOLVER);

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
        String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                + ClassUtils.convertClassNameToResourcePath(packageToScan) + "**/*.class";
        Resource[] resources = RESOURCE_RESOLVER.getResources(pattern);

        List<String> entityClasses = new ArrayList<>();
        for (Resource resource : resources) {
            if (resource.isReadable()) {
                MetadataReader metadataReader = METADATA_READER_FACTORY.getMetadataReader(resource);
                if (metadataReader.getAnnotationMetadata().hasAnnotation(Entity.class.getName())) {
                    entityClasses.add(metadataReader.getClassMetadata().getClassName());
                }
            } else {
                throw new IllegalStateException("Resource " + resource.getFilename() + " is not readable!");
            }
        }

        return entityClasses.toArray(new String[]{});
    }
}
