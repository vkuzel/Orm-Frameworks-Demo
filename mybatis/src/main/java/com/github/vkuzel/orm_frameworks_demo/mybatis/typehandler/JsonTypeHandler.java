package com.github.vkuzel.orm_frameworks_demo.mybatis.typehandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.postgresql.util.PGobject;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JsonTypeHandler implements TypeHandler<JsonNode> {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    @Override
    public void setParameter(PreparedStatement ps, int i, JsonNode parameter, JdbcType jdbcType) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonNode getResult(ResultSet rs, String columnName) throws SQLException {
        if (rs.wasNull()) {
            return null;
        }

        String value = ((PGobject) rs.getObject(columnName)).getValue();
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
    public JsonNode getResult(ResultSet rs, int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonNode getResult(CallableStatement cs, int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }
}
