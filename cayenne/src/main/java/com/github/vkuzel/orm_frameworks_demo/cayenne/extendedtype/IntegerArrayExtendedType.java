package com.github.vkuzel.orm_frameworks_demo.cayenne.extendedtype;

import org.apache.cayenne.access.types.ExtendedType;

import java.sql.*;

public class IntegerArrayExtendedType implements ExtendedType {
    @Override
    public String getClassName() {
        return "java.lang.Integer[]";
    }

    @Override
    public void setJdbcObject(PreparedStatement statement, Object value, int pos, int type, int scale) throws Exception {
        if (value == null) {
            statement.setNull(pos, Types.ARRAY);
        } else {
            Array array = statement.getConnection().createArrayOf("int", (Object[]) value);
            statement.setArray(pos, array);
        }
    }

    @Override
    public Object materializeObject(ResultSet rs, int index, int type) throws Exception {
        Array array = rs.getArray(index);
        if (array == null) {
            return null;
        }
        return array.getArray();
    }

    @Override
    public Object materializeObject(CallableStatement rs, int index, int type) throws Exception {
        throw new UnsupportedOperationException();
    }
}
