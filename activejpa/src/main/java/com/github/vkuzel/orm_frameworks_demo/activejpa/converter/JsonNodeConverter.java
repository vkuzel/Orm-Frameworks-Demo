package com.github.vkuzel.orm_frameworks_demo.activejpa.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.util.PGobject;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.sql.SQLException;

@Converter(autoApply = true)
public class JsonNodeConverter implements AttributeConverter<JsonNode, PGobject> {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    @Override
    public PGobject convertToDatabaseColumn(JsonNode jsonNode) {
        if (jsonNode == null) {
            return null;
        }

        try {
            String json = toJsonString(jsonNode);
            PGobject pGobject = new PGobject();
            pGobject.setValue(json);
            pGobject.setType("json");
            return pGobject;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public JsonNode convertToEntityAttribute(PGobject pGobject) {
        if (pGobject == null) {
            return null;
        }

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
