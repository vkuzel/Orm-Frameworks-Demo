package com.github.vkuzel.orm_frameworks_demo.openjpa.mapping;

import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneType;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.meta.strats.AbstractValueHandler;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.postgresql.util.PGobject;

import java.sql.SQLException;

public class PlaneTypeValueHandler extends AbstractValueHandler {
    @Override
    public Column[] map(ValueMapping vm, String name, ColumnIO io, boolean adapt) {
        // @see PlaneDimensionsHandler.map()
        return MappingHelper.createColumn(-1, -1, name);
    }

    @Override
    public Object toDataStoreValue(ValueMapping vm, Object val, JDBCStore store) {
        if (val == null) {
            return null;
        }

        try {
            PGobject pGobject = new PGobject();
            pGobject.setType("plane_type");
            pGobject.setValue(((DetailPlaneType) val).name());
            return pGobject;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Object toObjectValue(ValueMapping vm, Object val) {
        if (val == null) {
            return null;
        }

        return DetailPlaneType.valueOf((String) val);
    }

    @Override
    public Object toObjectValue(ValueMapping vm, Object val, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch) throws SQLException {
        throw new UnsupportedOperationException();
    }
}
