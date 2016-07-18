package com.github.vkuzel.orm_frameworks_demo.mybatis.typehandler;

import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneType;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.postgresql.util.PGobject;

import java.sql.*;

public class PlaneTypeTypeHandler implements TypeHandler<DetailPlaneType> {
    @Override
    public void setParameter(PreparedStatement ps, int i, DetailPlaneType parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null) {
            ps.setNull(i, Types.OTHER);
        } else {
            PGobject pGobject = new PGobject();
            pGobject.setType("plane_type");
            pGobject.setValue(parameter.name());
            ps.setObject(i, pGobject);
        }
    }

    @Override
    public DetailPlaneType getResult(ResultSet rs, String columnName) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public DetailPlaneType getResult(ResultSet rs, int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public DetailPlaneType getResult(CallableStatement cs, int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }
}
