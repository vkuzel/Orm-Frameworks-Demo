package com.github.vkuzel.orm_frameworks_demo.ebean;

import com.avaje.ebean.enhance.agent.Transformer;
import com.avaje.ebean.enhance.ant.OfflineFileTransform;
import com.avaje.ebean.enhance.ant.TransformationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class EntityEnhancer {

    private static final Logger log = LoggerFactory.getLogger(EntityEnhancer.class);
    private static final ClassLoader CLASS_LOADER = EntityEnhancer.class.getClassLoader();
    private static final TransformationListener TRANSFORMATION_LISTENER = new TransformationListener() {

        public void logEvent(String msg) {
            log.info(msg);
        }

        public void logError(String msg) {
            log.error(msg);
        }
    };

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            throw new IllegalArgumentException("Expected two arguments <class_dir> <package_to_scan>!");
        }
        String classDir = args[0];
        String packageToScan = args[1];

        Transformer transformer = new Transformer(classDir, "");
        OfflineFileTransform offlineFileTransform = new OfflineFileTransform(transformer, CLASS_LOADER, classDir);
        offlineFileTransform.setListener(TRANSFORMATION_LISTENER);
        offlineFileTransform.process(packageToScan);

        transformer.getUnexpectedExceptions().forEach((s, throwables) -> {
            log.error(s);
            throwables.forEach(Throwable::printStackTrace);
        });
    }
}
