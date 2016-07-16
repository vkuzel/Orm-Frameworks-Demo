package com.github.vkuzel.orm_frameworks_demo.jooq.binding;

import com.github.vkuzel.orm_frameworks_demo.jooq.udt.records.PlaneDimensionsRecord;
import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneDimensions;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Types;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaneDimensionsBinding implements Binding<PlaneDimensionsRecord, DetailPlaneDimensions> {

    private static final Pattern PG_STRING_PATTERN = Pattern.compile("^\\(([0-9.]+),([0-9.]+),([0-9.]+)\\)$");

    @Override
    public Converter<PlaneDimensionsRecord, DetailPlaneDimensions> converter() {
        return new Converter<PlaneDimensionsRecord, DetailPlaneDimensions>() {
            @Override
            public DetailPlaneDimensions from(PlaneDimensionsRecord databaseObject) {
                return new DetailPlaneDimensions(
                        databaseObject.getLengthMeters(),
                        databaseObject.getWingspanMeters(),
                        databaseObject.getHeightMeters()
                );
            }

            @Override
            public PlaneDimensionsRecord to(DetailPlaneDimensions userObject) {
                return new PlaneDimensionsRecord(
                        userObject.getLengthMeters(),
                        userObject.getWingspanMeters(),
                        userObject.getHeightMeters()
                );
            }

            @Override
            public Class<PlaneDimensionsRecord> fromType() {
                return PlaneDimensionsRecord.class;
            }

            @Override
            public Class<DetailPlaneDimensions> toType() {
                return DetailPlaneDimensions.class;
            }
        };
    }

    @Override
    public void sql(BindingSQLContext<DetailPlaneDimensions> ctx) throws SQLException {
        ctx.render().visit(DSL.val(ctx.convert(converter()).value())).sql("::plane_dimensions");
    }

    @Override
    public void register(BindingRegisterContext<DetailPlaneDimensions> ctx) throws SQLException {
        ctx.statement().registerOutParameter(ctx.index(), Types.OTHER);
    }

    @Override
    public void set(BindingSetStatementContext<DetailPlaneDimensions> ctx) throws SQLException {
        ctx.statement().setString(ctx.index(), Objects.toString(ctx.convert(converter()).value(), null));
    }

    @Override
    public void set(BindingSetSQLOutputContext<DetailPlaneDimensions> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void get(BindingGetResultSetContext<DetailPlaneDimensions> ctx) throws SQLException {
        String pgString = ctx.resultSet().getString(ctx.index());
        PlaneDimensionsRecord record = fromString(pgString);
        ctx.convert(converter()).value(record);
    }

    @Override
    public void get(BindingGetStatementContext<DetailPlaneDimensions> ctx) throws SQLException {
        String pgString = ctx.statement().getString(ctx.index());
        PlaneDimensionsRecord record = fromString(pgString);
        ctx.convert(converter()).value(record);
    }

    private PlaneDimensionsRecord fromString(Object databaseObject) {
        String pgString = (String) databaseObject;
        Matcher matcher = PG_STRING_PATTERN.matcher(pgString);
        if (matcher.find()) {
            return new PlaneDimensionsRecord(
                    new BigDecimal(matcher.group(1)),
                    new BigDecimal(matcher.group(2)),
                    new BigDecimal(matcher.group(3))
            );
        } else {
            throw new IllegalArgumentException(pgString);
        }
    }

    @Override
    public void get(BindingGetSQLInputContext<DetailPlaneDimensions> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
}
