package com.github.vkuzel.orm_frameworks_demo.openjpa.mapping;

import com.google.common.base.CaseFormat;
import org.apache.openjpa.jdbc.identifier.DBIdentifier;
import org.apache.openjpa.jdbc.schema.Column;

public class MappingHelper {

    public static Column[] createColumn(int javaType, int sqlType, String fieldName) {
        Column column = new Column();
        String columnName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName);
        column.setIdentifier(DBIdentifier.newColumn(columnName));
        column.setJavaType(javaType);
        column.setType(sqlType);
        column.setTypeIdentifier(DBIdentifier.newColumnDefinition("plane_dimensions"));

        return new Column[]{column};
    }
}
