package com.github.vkuzel.orm_frameworks_demo.common;

import com.google.common.io.CharStreams;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStreamReader;

public class Utils {

    private Utils() {
        throw new AssertionError();
    }

    public static String loadResourceToString(Resource resource) {
        try (InputStreamReader reader = new InputStreamReader(resource.getInputStream())) {
            return CharStreams.toString(reader);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
