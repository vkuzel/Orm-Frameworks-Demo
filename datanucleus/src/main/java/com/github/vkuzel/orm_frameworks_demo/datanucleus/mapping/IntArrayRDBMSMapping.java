package com.github.vkuzel.orm_frameworks_demo.datanucleus.mapping;

import org.datanucleus.store.rdbms.RDBMSStoreManager;
import org.datanucleus.store.rdbms.mapping.datastore.AbstractDatastoreMapping;
import org.datanucleus.store.rdbms.mapping.java.JavaTypeMapping;
import org.datanucleus.store.rdbms.table.Column;

import java.sql.*;

public class IntArrayRDBMSMapping extends AbstractDatastoreMapping {

    public IntArrayRDBMSMapping(JavaTypeMapping mapping, RDBMSStoreManager storeMgr, Column col) {
        super(storeMgr, mapping);
        column = col;
    }

    @Override
    public int getJDBCType() {
        return Types.ARRAY;
    }

    @Override
    public void setObject(PreparedStatement ps, int exprIndex, Object value) {
        try {
            if (value == null) {
                ps.setNull(exprIndex, Types.ARRAY);
            } else {
                Array array = ps.getConnection().createArrayOf("int", (Integer[]) value);
                ps.setArray(exprIndex, array);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Object getObject(ResultSet resultSet, int exprIndex) {
        try {
            if (resultSet.wasNull()) {
                return null;
            }

            return resultSet.getArray(exprIndex).getArray();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
