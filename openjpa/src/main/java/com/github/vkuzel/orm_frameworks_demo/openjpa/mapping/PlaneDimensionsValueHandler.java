package com.github.vkuzel.orm_frameworks_demo.openjpa.mapping;

import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneDimensions;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.meta.strats.AbstractValueHandler;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.postgresql.util.PGobject;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaneDimensionsValueHandler extends AbstractValueHandler {

    private static final Pattern PG_STRING_PATTERN = Pattern.compile("^\\(([0-9.]+),([0-9.]+),([0-9.]+)\\)$");

    @Override
    public Column[] map(ValueMapping vm, String name, ColumnIO io, boolean adapt) {
        // Hack: Type codes -1 are here so value created by this mapper can
        // fall through switch statement in DBDictionary.setTyped method to
        // PgPreparedStatement.setPGObject.
        return MappingHelper.createColumn(-1, -1, name);
    }

    @Override
    public Object toDataStoreValue(ValueMapping vm, Object val, JDBCStore store) {
        if (val == null) {
            return null;
        }

        try {
            PGobject pGobject = new PGobject();
            pGobject.setType("plane_dimensions");
            pGobject.setValue(toPgString((DetailPlaneDimensions) val));
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

        PGobject pGobject = (PGobject) val;
        return fromPgString(pGobject.getValue());
    }

    @Override
    public Object toObjectValue(ValueMapping vm, Object val, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch) throws SQLException {
        throw new UnsupportedOperationException();
    }

    private String toPgString(DetailPlaneDimensions dimensions) {
        return String.format("(%f,%f,%f)", dimensions.getLengthMeters(), dimensions.getWingspanMeters(), dimensions.getHeightMeters());
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
}
