package com.github.vkuzel.orm_frameworks_demo.openjpa.mapping;


import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.meta.strats.AbstractValueHandler;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.meta.JavaTypes;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class LocalDateTimeValueHandler extends AbstractValueHandler {

    @Override
    public Column[] map(ValueMapping vm, String name, ColumnIO io, boolean adapt) {
        return MappingHelper.createColumn(JavaTypes.DATE, Types.TIMESTAMP, name);
    }

    @Override
    public Object toDataStoreValue(ValueMapping vm, Object val, JDBCStore store) {
        if (val == null) {
            return null;
        }

        return Timestamp.valueOf((LocalDateTime) val);
    }

    @Override
    public Object toObjectValue(ValueMapping vm, Object val) {
        if (val == null) {
            return null;
        }

        Instant instant = ((Date) val).toInstant();
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    @Override
    public Object toObjectValue(ValueMapping vm, Object val, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch) throws SQLException {
        throw new UnsupportedOperationException();
    }
}
