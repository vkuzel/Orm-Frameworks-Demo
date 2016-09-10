package com.github.vkuzel.orm_frameworks_demo.datanucleus.mapping;

import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneType;
import org.datanucleus.store.rdbms.RDBMSStoreManager;
import org.datanucleus.store.rdbms.mapping.datastore.AbstractDatastoreMapping;
import org.datanucleus.store.rdbms.mapping.java.JavaTypeMapping;
import org.datanucleus.store.rdbms.table.Column;
import org.postgresql.util.PGobject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class PlaneTypeRDBMSMapping extends AbstractDatastoreMapping {

    public PlaneTypeRDBMSMapping(JavaTypeMapping mapping, RDBMSStoreManager storeMgr, Column col) {
        super(storeMgr, mapping);
        column = col;
    }

    @Override
    public int getJDBCType() {
        return Types.OTHER;
    }

    @Override
    public void setObject(PreparedStatement ps, int exprIndex, Object value) {
        try {
            if (value == null) {
                ps.setNull(exprIndex, Types.OTHER);
            } else {
                PGobject pGobject = new PGobject();
                pGobject.setType("plane_type");
                pGobject.setValue(((DetailPlaneType) value).name());
                ps.setObject(exprIndex, pGobject);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Object getObject(ResultSet resultSet, int exprIndex) {
        try {
            String value = resultSet.getString(exprIndex);
            if (value == null) {
                return null;
            }

            return DetailPlaneType.valueOf(value);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
