package com.github.vkuzel.orm_frameworks_demo.cayenne.extendedtype;

import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneDimensions;
import org.apache.cayenne.access.types.ExtendedType;
import org.postgresql.util.PGobject;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaneDimensionsExtendedType implements ExtendedType {

    private static final Pattern PG_STRING_PATTERN = Pattern.compile("^\\(([0-9.]+),([0-9.]+),([0-9.]+)\\)$");

    @Override
    public String getClassName() {
        return "com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneDimensions";
    }

    @Override
    public void setJdbcObject(PreparedStatement statement, Object value, int pos, int type, int scale) throws Exception {
        if (value == null) {
            statement.setNull(pos, Types.OTHER);
        } else {
            PGobject pGobject = new PGobject();
            pGobject.setType("plane_dimensions");
            pGobject.setValue(toPgString((DetailPlaneDimensions) value));
            statement.setObject(pos, pGobject);
        }
    }

    private String toPgString(DetailPlaneDimensions dimensions) {
        return String.format("(%f,%f,%f)", dimensions.getLengthMeters(), dimensions.getWingspanMeters(), dimensions.getHeightMeters());
    }

    @Override
    public Object materializeObject(ResultSet rs, int index, int type) throws Exception {
        Object object = rs.getObject(index);
        if (object == null) {
            return null;
        }
        return fromPgString(((PGobject) object).getValue());
    }

    private DetailPlaneDimensions fromPgString(String pgString) {
        Matcher matcher = PG_STRING_PATTERN.matcher(pgString);
        if (matcher.find()) {
            return new DetailPlaneDimensions(
                    new BigDecimal(matcher.group(1)),
                    new BigDecimal(matcher.group(2)),
                    new BigDecimal(matcher.group(3))
            );
        } else {
            throw new IllegalArgumentException(pgString);
        }
    }

    @Override
    public Object materializeObject(CallableStatement rs, int index, int type) throws Exception {
        throw new UnsupportedOperationException();
    }
}
