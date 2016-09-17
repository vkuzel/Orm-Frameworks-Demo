package com.github.vkuzel.orm_frameworks_demo.ebean.type;

import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneDimensions;
import org.postgresql.util.PGobject;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaneDimensionsScalarType extends AbstractOtherScalarType<DetailPlaneDimensions> {

    private static final Pattern PG_STRING_PATTERN = Pattern.compile("^\\(([0-9.]+),([0-9.]+),([0-9.]+)\\)$");

    public PlaneDimensionsScalarType() {
        super(DetailPlaneDimensions.class);
    }

    @Override
    public Object toJdbcType(Object value) {
        if (value instanceof PGobject) {
            return value;
        } else if (value instanceof DetailPlaneDimensions) {
            try {
                PGobject pGobject = new PGobject();
                pGobject.setType("plane_dimensions");
                pGobject.setValue(formatValue((DetailPlaneDimensions) value));
                return pGobject;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
        throw new IllegalArgumentException("Unknown type " + value.getClass().getName());
    }

    @Override
    public DetailPlaneDimensions toBeanType(Object value) {
        if (value instanceof DetailPlaneDimensions) {
            return (DetailPlaneDimensions) value;
        } else if (value instanceof PGobject) {
            PGobject pGobject = (PGobject) value;
            return parse(pGobject.getValue());
        }
        throw new IllegalArgumentException("Unknown type " + value.getClass().getName());
    }

    @Override
    public String formatValue(DetailPlaneDimensions value) {
        return String.format("(%f,%f,%f)", value.getLengthMeters(), value.getWingspanMeters(), value.getHeightMeters());
    }

    @Override
    public DetailPlaneDimensions parse(String value) {
        Matcher matcher = PG_STRING_PATTERN.matcher(value);
        if (matcher.find()) {
            return new DetailPlaneDimensions(
                    new BigDecimal(matcher.group(1)),
                    new BigDecimal(matcher.group(2)),
                    new BigDecimal(matcher.group(3))
            );
        } else {
            throw new IllegalArgumentException(value);
        }
    }
}
