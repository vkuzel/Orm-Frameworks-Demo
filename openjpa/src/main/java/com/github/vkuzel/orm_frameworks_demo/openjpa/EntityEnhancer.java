package com.github.vkuzel.orm_frameworks_demo.openjpa;

import org.apache.commons.lang.Validate;
import org.apache.openjpa.conf.OpenJPAConfigurationImpl;
import org.apache.openjpa.enhance.PCEnhancer;
import org.apache.openjpa.meta.MetaDataRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import javax.persistence.Entity;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EntityEnhancer {

    private static final PathMatchingResourcePatternResolver RESOURCE_RESOLVER = new PathMatchingResourcePatternResolver();

    private static final MetadataReaderFactory METADATA_READER_FACTORY = new CachingMetadataReaderFactory(RESOURCE_RESOLVER);

    public static void main(String[] args) throws IOException {
        Validate.isTrue(args.length == 2, "Expected two arguments <output_directory> <package_to_scan>!");
        File outputDir = new File(args[0]);
        Validate.isTrue(outputDir.exists(), "Output directory " + outputDir.getAbsolutePath() + " does not exist!");
        String[] classesToEnhance = findEntityClasses(args[1]);
        Validate.isTrue(classesToEnhance.length > 0, "No classes to enhance has been found!");

        runEnhancer(outputDir, classesToEnhance);
    }

    private static void runEnhancer(File outputDir, String[] classesToEnhance) throws IOException {
        PCEnhancer.Flags flags = new PCEnhancer.Flags();
        flags.directory = outputDir;

        OpenJPAConfigurationImpl configuration = new OpenJPAConfigurationImpl();
        configuration.setDeferResourceLoading(true);
        configuration.setMetaDataFactory("jpa");

        MetaDataRepository metaDataRepository = configuration.newMetaDataRepositoryInstance();

        PCEnhancer.run(configuration, classesToEnhance, flags, metaDataRepository, null, EntityEnhancer.class.getClassLoader());
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
