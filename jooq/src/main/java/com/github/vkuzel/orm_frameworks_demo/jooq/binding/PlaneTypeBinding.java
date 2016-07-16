package com.github.vkuzel.orm_frameworks_demo.jooq.binding;

import com.github.vkuzel.orm_frameworks_demo.jooq.enums.PlaneType;
import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneType;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Types;
import java.util.Objects;

public class PlaneTypeBinding implements Binding<PlaneType, DetailPlaneType> {
    @Override
    public Converter<PlaneType, DetailPlaneType> converter() {
        return new Converter<PlaneType, DetailPlaneType>() {
            @Override
            public DetailPlaneType from(PlaneType databaseObject) {
                return DetailPlaneType.valueOf(databaseObject.getLiteral());
            }

            @Override
            public PlaneType to(DetailPlaneType userObject) {
                return PlaneType.valueOf(userObject.name());
            }

            @Override
            public Class<PlaneType> fromType() {
                return PlaneType.class;
            }

            @Override
            public Class<DetailPlaneType> toType() {
                return DetailPlaneType.class;
            }
        };
    }

    @Override
    public void sql(BindingSQLContext<DetailPlaneType> ctx) throws SQLException {
        ctx.render().visit(DSL.val(ctx.convert(converter()).value())).sql("::plane_type");
    }

    @Override
    public void register(BindingRegisterContext<DetailPlaneType> ctx) throws SQLException {
        ctx.statement().registerOutParameter(ctx.index(), Types.OTHER);
    }

    @Override
    public void set(BindingSetStatementContext<DetailPlaneType> ctx) throws SQLException {
        ctx.statement().setString(ctx.index(), Objects.toString(ctx.convert(converter()).value(), null));
    }

    @Override
    public void set(BindingSetSQLOutputContext<DetailPlaneType> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void get(BindingGetResultSetContext<DetailPlaneType> ctx) throws SQLException {
        String pgString = ctx.resultSet().getString(ctx.index());
        PlaneType planeType = PlaneType.valueOf(pgString);
        ctx.convert(converter()).value(planeType);
    }

    @Override
    public void get(BindingGetStatementContext<DetailPlaneType> ctx) throws SQLException {
        String pgString = ctx.statement().getString(ctx.index());
        PlaneType planeType = PlaneType.valueOf(pgString);
        ctx.convert(converter()).value(planeType);
    }

    @Override
    public void get(BindingGetSQLInputContext<DetailPlaneType> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
}
