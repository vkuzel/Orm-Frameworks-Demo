package com.github.vkuzel.orm_frameworks_demo.datanucleus.mapping;

import org.datanucleus.store.rdbms.mapping.java.SingleFieldMapping;

public class IntArrayMapping extends SingleFieldMapping {

    @Override
    public Class getJavaType() {
        return Integer[].class;
    }
}
