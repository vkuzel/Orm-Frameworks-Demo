package com.github.vkuzel.orm_frameworks_demo.openjpa.mapping;

import com.github.vkuzel.orm_frameworks_demo.openjpa.domain.SerializableJson;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.meta.strats.AbstractValueHandler;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.postgresql.util.PGobject;

import java.sql.SQLException;

// Jackson's JsonNode does not implement Serializable interface so OpenJPA has
// hard times to handle it. So I wrapped it with custom SerializableJson class
// that works properly.
public class SerializableJsonValueHandler extends AbstractValueHandler {

    @Override
    public Column[] map(ValueMapping vm, String name, ColumnIO io, boolean adapt) {
        return MappingHelper.createColumn(-1, -1, name);
    }

    @Override
    public Object toDataStoreValue(ValueMapping vm, Object val, JDBCStore store) {
        if (val == null) {
            return null;
        }

        try {
            String json = ((SerializableJson) val).getValue();

            PGobject pGobject = new PGobject();
            pGobject.setValue(json);
            pGobject.setType("json");
            return pGobject;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Object toObjectValue(ValueMapping vm, Object val) {
        String json = ((PGobject) val).getValue();
        return new SerializableJson(json);
    }

    @Override
    public Object toObjectValue(ValueMapping vm, Object val, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch) throws SQLException {
        throw new UnsupportedOperationException();
    }
}
