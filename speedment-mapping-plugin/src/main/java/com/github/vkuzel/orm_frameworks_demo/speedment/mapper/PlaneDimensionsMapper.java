package com.github.vkuzel.orm_frameworks_demo.speedment.mapper;

import com.github.vkuzel.orm_frameworks_demo.speedment.transport.PlaneDimensions;
import com.speedment.config.db.mapper.TypeMapper;
import org.postgresql.util.PGobject;

import java.math.BigDecimal;
import java.sql.SQLException;

public class PlaneDimensionsMapper implements TypeMapper<Object, PlaneDimensions> {
    @Override
    public Class<PlaneDimensions> getJavaType() {
        return PlaneDimensions.class;
    }

    @Override
    public Class<Object> getDatabaseType() {
        return Object.class;
    }

    @Override
    public PlaneDimensions toJavaType(Object value) {
        if (value == null) {
            return null;
        }

        PlaneDimensions dimensions = new PlaneDimensions();
        dimensions.setHeightMeters(new BigDecimal(10));
        dimensions.setLengthMeters(new BigDecimal(20));
        dimensions.setWingspanMeters(new BigDecimal(30));
        return dimensions;
    }

    @Override
    public Object toDatabaseType(PlaneDimensions value) {
        if (value == null) {
            return null;
        }

        PGobject pGobject = new PGobject();
        pGobject.setType("plane_dimensions");
        try {
            pGobject.setValue("(1,2,3)");
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return pGobject;
    }

    @Override
    public boolean isIdentityMapper() {
        return false;
    }
}
