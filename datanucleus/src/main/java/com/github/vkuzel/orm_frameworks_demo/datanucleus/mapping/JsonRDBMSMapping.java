package com.github.vkuzel.orm_frameworks_demo.datanucleus.mapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.datanucleus.store.rdbms.RDBMSStoreManager;
import org.datanucleus.store.rdbms.mapping.datastore.AbstractDatastoreMapping;
import org.datanucleus.store.rdbms.mapping.java.JavaTypeMapping;
import org.datanucleus.store.rdbms.table.Column;
import org.postgresql.util.PGobject;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class JsonRDBMSMapping extends AbstractDatastoreMapping {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    public JsonRDBMSMapping(JavaTypeMapping mapping, RDBMSStoreManager storeMgr, Column col) {
        super(storeMgr, mapping);
        column = col;
    }

    @Override
    public int getJDBCType() {
        return Types.OTHER;
    }

    @Override
    public Object getObject(ResultSet resultSet, int exprIndex) {
        try {
            if (resultSet.wasNull()) {
                return null;
            }

            String value = ((PGobject) resultSet.getObject(exprIndex)).getValue();
            return fromJsonString(value);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void setObject(PreparedStatement ps, int exprIndex, Object value) {
        try {
            if (value == null) {
                ps.setNull(exprIndex, Types.OTHER);
            } else {
                String json = toJsonString((JsonNode) value);

                PGobject pGobject = new PGobject();
                pGobject.setValue(json);
                pGobject.setType("json");
                ps.setObject(exprIndex, pGobject);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static JsonNode fromPGObject(PGobject pGobject) {
        String value = pGobject.getValue();
        return fromJsonString(value);
    }

    private static String toJsonString(JsonNode node) {
        try {
            return JSON_MAPPER.writeValueAsString(node);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    private static JsonNode fromJsonString(String json) {
        try {
            return JSON_MAPPER.readValue(json, JsonNode.class);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
