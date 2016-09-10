package com.github.vkuzel.orm_frameworks_demo.datanucleus.mapping;

import com.fasterxml.jackson.databind.JsonNode;
import org.datanucleus.store.rdbms.mapping.java.SingleFieldMapping;

public class JsonMapping extends SingleFieldMapping {
    @Override
    public Class getJavaType() {
        return JsonNode.class;
    }
}
