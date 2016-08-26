package com.github.vkuzel.orm_frameworks_demo.openjpa.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;

public class SerializableJson implements Serializable {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    private final String value;

    public SerializableJson(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SerializableJson fromJsonNode(JsonNode node) {
        try {
            return new SerializableJson(JSON_MAPPER.writeValueAsString(node));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    public JsonNode toJsonNode() {
        try {
            return JSON_MAPPER.readValue(value, JsonNode.class);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
