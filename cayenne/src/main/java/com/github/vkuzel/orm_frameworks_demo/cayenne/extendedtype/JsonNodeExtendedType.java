package com.github.vkuzel.orm_frameworks_demo.cayenne.extendedtype;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.cayenne.access.types.ExtendedType;
import org.postgresql.util.PGobject;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

public class JsonNodeExtendedType implements ExtendedType {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    @Override
    public String getClassName() {
        return "com.fasterxml.jackson.databind.JsonNode";
    }

    @Override
    public void setJdbcObject(PreparedStatement statement, Object value, int pos, int type, int scale) throws Exception {
        if (value == null) {
            statement.setNull(pos, Types.OTHER);
        } else {
            String json = toJsonString((JsonNode) value);

            PGobject pGobject = new PGobject();
            pGobject.setValue(json);
            pGobject.setType("json");
            statement.setObject(pos, pGobject);
        }
    }

    private String toJsonString(JsonNode node) {
        try {
            return JSON_MAPPER.writeValueAsString(node);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Object materializeObject(ResultSet rs, int index, int type) throws Exception {
        Object object = rs.getObject(index);
        if (object == null) {
            return null;
        }
        String value = ((PGobject) object).getValue();
        return fromJsonString(value);
    }

    private JsonNode fromJsonString(String json) {
        try {
            return JSON_MAPPER.readValue(json, JsonNode.class);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Object materializeObject(CallableStatement rs, int index, int type) throws Exception {
        throw new UnsupportedOperationException();
    }
}
