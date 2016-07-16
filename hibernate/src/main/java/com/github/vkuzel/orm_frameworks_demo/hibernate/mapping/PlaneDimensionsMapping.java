package com.github.vkuzel.orm_frameworks_demo.hibernate.mapping;

import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneDimensions;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;
import org.postgresql.util.PGobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaneDimensionsMapping implements UserType {

    private static final Pattern PG_STRING_PATTERN = Pattern.compile("^\\(([0-9.]+),([0-9.]+),([0-9.]+)\\)$");

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.OTHER};
    }

    @Override
    public Class returnedClass() {
        return DetailPlaneDimensions.class;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        if (rs.wasNull()) {
            return null;
        }

        PGobject pGobject = (PGobject) rs.getObject(names[0]);
        return fromPgString(pGobject.getValue());
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.OTHER);
        } else {
            PGobject pGobject = new PGobject();
            pGobject.setType("plane_dimensions");
            pGobject.setValue(toPgString((DetailPlaneDimensions) value));
            st.setObject(index, pGobject);
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

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y) {
            return true;
        } else if (x == null || y == null) {
            return false;
        } else {
            return x.equals(y);
        }
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        DetailPlaneDimensions dimensions = (DetailPlaneDimensions) value;
        return new DetailPlaneDimensions(
                dimensions.getLengthMeters(),
                dimensions.getWingspanMeters(),
                dimensions.getHeightMeters()
        );
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return null;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return null;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return deepCopy(original);
    }
}
