package com.github.vkuzel.orm_frameworks_demo.cayenne.extendedtype;

import org.apache.cayenne.access.types.ExtendedType;

import java.sql.*;
import java.time.LocalDateTime;

public class LocalDateTimeExtendedType implements ExtendedType {
    @Override
    public String getClassName() {
        return "java.time.LocalDateTime";
    }

    @Override
    public void setJdbcObject(PreparedStatement statement, Object value, int pos, int type, int scale) throws Exception {
        if (value == null) {
            statement.setNull(pos, Types.TIMESTAMP);
        } else {
            LocalDateTime dateTime = (LocalDateTime) value;
            statement.setTimestamp(pos, Timestamp.valueOf(dateTime));
        }
    }

    @Override
    public Object materializeObject(ResultSet rs, int index, int type) throws Exception {
        Timestamp timestamp = rs.getTimestamp(index);
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }

    @Override
    public Object materializeObject(CallableStatement rs, int index, int type) throws Exception {
        throw new UnsupportedOperationException();
    }
}
