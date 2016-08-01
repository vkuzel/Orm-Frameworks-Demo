package com.github.vkuzel.orm_frameworks_demo.cayenne.extendedtype;

import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneType;
import org.apache.cayenne.access.types.ExtendedType;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

public class PlaneTypeExtendedType implements ExtendedType {
    @Override
    public String getClassName() {
        return "com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneType";
    }

    @Override
    public void setJdbcObject(PreparedStatement statement, Object value, int pos, int type, int scale) throws Exception {
        if (value == null) {
            statement.setNull(pos, Types.OTHER);
        } else {
            PGobject pGobject = new PGobject();
            pGobject.setType("plane_type");
            pGobject.setValue(((DetailPlaneType) value).name());
            statement.setObject(pos, pGobject);
        }
    }

    @Override
    public Object materializeObject(ResultSet rs, int index, int type) throws Exception {
        String string = rs.getString(index);
        if (string == null) {
            return null;
        }
        return DetailPlaneType.valueOf(string);
    }

    @Override
    public Object materializeObject(CallableStatement rs, int index, int type) throws Exception {
        throw new UnsupportedOperationException();
    }
}
