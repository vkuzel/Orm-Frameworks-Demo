package com.github.vkuzel.orm_frameworks_demo.ebean.type;

import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneType;
import io.ebeaninternal.server.type.ScalarTypeEnum;
import org.postgresql.util.PGobject;

import javax.persistence.EnumType;
import java.sql.SQLException;
import java.util.Set;

public class PlaneTypeScalarType extends AbstractOtherScalarType<DetailPlaneType> implements ScalarTypeEnum<DetailPlaneType> {

    public PlaneTypeScalarType() {
        super(DetailPlaneType.class);
    }

    @Override
    public Object toJdbcType(Object value) {
        if (value instanceof PGobject) {
            return value;
        } else if (value instanceof DetailPlaneType) {
            try {
                PGobject pGobject = new PGobject();
                pGobject.setType("plane_type");
                pGobject.setValue(formatValue((DetailPlaneType) value));
                return pGobject;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
        throw new IllegalArgumentException("Unknown type " + value.getClass().getName());
    }

    @Override
    public DetailPlaneType toBeanType(Object value) {
        if (value instanceof DetailPlaneType) {
            return (DetailPlaneType)value;
        } else if (value instanceof PGobject) {
            PGobject pGobject = (PGobject) value;
            return parse(pGobject.getValue());
        } else if (value instanceof String) {
            String string = (String) value;
            return parse(string);
        }
        throw new IllegalArgumentException("Unknown type " + value.getClass().getName());
    }

    @Override
    public String formatValue(DetailPlaneType value) {
        return value.name();
    }

    @Override
    public DetailPlaneType parse(String value) {
        return DetailPlaneType.valueOf(value);
    }

    @Override
    public Set<String> getDbCheckConstraintValues() {
        return null;
    }

    @Override
    public boolean isCompatible(EnumType enumType) {
        return false;
    }
}
