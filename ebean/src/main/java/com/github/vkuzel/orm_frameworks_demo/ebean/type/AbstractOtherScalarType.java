package com.github.vkuzel.orm_frameworks_demo.ebean.type;

import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.type.DataReader;
import com.avaje.ebeaninternal.server.type.ScalarTypeBase;
import com.avaje.ebeanservice.docstore.api.mapping.DocPropertyType;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;

public abstract class AbstractOtherScalarType<T> extends ScalarTypeBase<T> {
    public AbstractOtherScalarType(Class<T> type) {
        super(type, false, Types.OTHER);
    }

    @Override
    public T read(DataReader reader) throws SQLException {
        return toBeanType(reader.getObject());
    }

    @Override
    public void bind(DataBind bind, T value) throws SQLException {
        if (value == null) {
            bind.setNull(Types.OTHER);
        } else {
            bind.setObject(toJdbcType(value), Types.OTHER);
        }
    }

    @Override
    public DocPropertyType getDocType() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isDateTimeCapable() {
        return false;
    }

    @Override
    public T convertFromMillis(long dateTime) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T readData(DataInput dataInput) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeData(DataOutput dataOutput, T v) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public T jsonRead(JsonParser parser) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void jsonWrite(JsonGenerator writer, T value) throws IOException {
        throw new UnsupportedOperationException();
    }
}