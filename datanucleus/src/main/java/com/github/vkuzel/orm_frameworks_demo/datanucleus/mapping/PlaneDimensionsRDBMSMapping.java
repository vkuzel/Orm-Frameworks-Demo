package com.github.vkuzel.orm_frameworks_demo.datanucleus.mapping;

import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneDimensions;
import org.datanucleus.store.rdbms.RDBMSStoreManager;
import org.datanucleus.store.rdbms.mapping.datastore.AbstractDatastoreMapping;
import org.datanucleus.store.rdbms.mapping.java.JavaTypeMapping;
import org.datanucleus.store.rdbms.table.Column;
import org.postgresql.util.PGobject;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaneDimensionsRDBMSMapping extends AbstractDatastoreMapping {

    private static final Pattern PG_STRING_PATTERN = Pattern.compile("^\\(([0-9.]+),([0-9.]+),([0-9.]+)\\)$");

    public PlaneDimensionsRDBMSMapping(JavaTypeMapping mapping, RDBMSStoreManager storeMgr, Column col) {
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
                pGobject.setType("plane_dimensions");
                pGobject.setValue(toPgString((DetailPlaneDimensions) value));
                ps.setObject(exprIndex, pGobject);
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

            PGobject pGobject = (PGobject) resultSet.getObject(exprIndex);
            return fromPgString(pGobject.getValue());
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
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
