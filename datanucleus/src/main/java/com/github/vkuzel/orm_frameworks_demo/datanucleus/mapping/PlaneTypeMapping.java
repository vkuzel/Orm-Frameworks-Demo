package com.github.vkuzel.orm_frameworks_demo.datanucleus.mapping;

import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneType;
import org.datanucleus.store.rdbms.mapping.java.SingleFieldMapping;

public class PlaneTypeMapping extends SingleFieldMapping {
    @Override
    public Class getJavaType() {
        return DetailPlaneType.class;
    }
}
