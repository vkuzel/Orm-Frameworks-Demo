package com.github.vkuzel.orm_frameworks_demo.mybatis.typehandler;

import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneDimensions;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.postgresql.util.PGobject;

import java.math.BigDecimal;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaneDimensionsTypeHandler implements TypeHandler<DetailPlaneDimensions> {

    private static final Pattern PG_STRING_PATTERN = Pattern.compile("^\\(([0-9.]+),([0-9.]+),([0-9.]+)\\)$");

    @Override
    public void setParameter(PreparedStatement ps, int i, DetailPlaneDimensions parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null) {
            ps.setNull(i, Types.OTHER);
        } else {
            PGobject pGobject = new PGobject();
            pGobject.setType("plane_dimensions");
            pGobject.setValue(toPgString(parameter));
            ps.setObject(i, pGobject);
        }
    }

    @Override
    public DetailPlaneDimensions getResult(ResultSet rs, String columnName) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public DetailPlaneDimensions getResult(ResultSet rs, int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public DetailPlaneDimensions getResult(CallableStatement cs, int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
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

    private String toPgString(DetailPlaneDimensions dimensions) {
        return String.format("(%f,%f,%f)", dimensions.getLengthMeters(), dimensions.getWingspanMeters(), dimensions.getHeightMeters());
    }
}
