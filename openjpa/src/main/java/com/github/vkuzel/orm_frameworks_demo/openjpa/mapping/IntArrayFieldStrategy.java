package com.github.vkuzel.orm_frameworks_demo.openjpa.mapping;

import org.apache.openjpa.jdbc.identifier.DBIdentifier;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.ValueMappingInfo;
import org.apache.openjpa.jdbc.meta.strats.AbstractFieldStrategy;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.sql.*;
import org.apache.openjpa.kernel.OpenJPAStateManager;

import java.io.Serializable;
import java.sql.Array;
import java.sql.SQLException;
import java.sql.Types;

public class IntArrayFieldStrategy extends AbstractFieldStrategy {

    @Override
    public void setFieldMapping(FieldMapping owner) {
        // Hack: We need to tell OpenJPA to handle array as a ordinary
        // serializable object not an array. So array value can pass
        // through FieldMetaData.isInDefaultFetchGroup() method.
        owner.setDeclaredType(Serializable.class);
        super.setFieldMapping(owner);
    }

    @Override
    public int select(Select sel, OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, int eagerMode) {
        sel.select(field.getColumns()[0], field.join(sel));
        return 1;
    }

    @Override
    public void map(boolean adapt) {
        ValueMappingInfo vinfo = field.getValueInfo();
        vinfo.assertNoJoin(field, true);
        vinfo.assertNoForeignKey(field, !adapt);

        DBDictionary dict = field.getMappingRepository().getDBDictionary();
        DBIdentifier fieldName = DBIdentifier.newColumn(field.getName(), dict != null && dict.delimitAll());

        Column[] tmpCols = MappingHelper.createColumn(-1, Types.ARRAY, field.getName());
        Column[] cols = vinfo.getColumns(
                field,
                fieldName,
                tmpCols,
                field.getTable(),
                adapt
        );

        field.setColumns(cols);
    }

    @Override
    public void insert(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
        if (!field.getColumnIO().isInsertable(0, false))
            return;

        Row row = field.getRow(sm, store, rm, Row.ACTION_INSERT);
        Column col = field.getColumns()[0];
        Object value = sm.fetchObject(field.getIndex());

        try {
            Array array = store.getConnection().createArrayOf("int", (Integer[]) value);
            row.setObject(col, array);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void update(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(OpenJPAStateManager sm, JDBCStore store, RowManager rm) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void load(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch, Result res) throws SQLException {
        Column col = field.getColumns()[0];
        if (!res.contains(col))
            return;

        int idx = field.getIndex();
        Array array = res.getArray(col);
        sm.storeObject(idx, array.getArray());
    }

    @Override
    public void load(OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch) throws SQLException {
        throw new UnsupportedOperationException();
    }
}
