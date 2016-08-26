package com.github.vkuzel.orm_frameworks_demo.openjpa;

import org.apache.commons.lang.Validate;
import org.apache.openjpa.conf.OpenJPAConfigurationImpl;
import org.apache.openjpa.enhance.PCEnhancer;
import org.apache.openjpa.meta.MetaDataRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.persistence.Entity;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ClassEnhancer {

    private static final PathMatchingResourcePatternResolver RESOURCE_RESOLVER = new PathMatchingResourcePatternResolver();

    private static final Predicate<Class> ENTITY_CLASS_FILTER = aClass -> aClass.isAnnotationPresent(Entity.class);

    public static void main(String[] args) throws IOException {
        Validate.isTrue(args.length > 1, "Expected two arguments <output_directory> <package_to_scan>!");
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

        PCEnhancer.run(configuration, classesToEnhance, flags, metaDataRepository, null, ClassEnhancer.class.getClassLoader());
    }

    private static String[] findEntityClasses(String packageToScan) throws IOException {
        String pattern = "classpath*:" + packageToScan.replace(".", "/") + "/*.class";
        Resource[] resources = RESOURCE_RESOLVER.getResources(pattern);

        return Arrays.asList(resources).stream()
                .map(r -> mapToClass(packageToScan, r)).filter(ENTITY_CLASS_FILTER).map(Class::getName)
                .collect(Collectors.toList()).toArray(new String[] {});
    }

    private static Class mapToClass(String packageToScan, Resource resource) {
        try {
            String resourceUri = resource.getURI().toString();
            resourceUri = resourceUri.replace(".class", "").replace("/", ".");
            resourceUri = resourceUri.substring(resourceUri.indexOf(packageToScan));
            return Class.forName(resourceUri);
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }
}
