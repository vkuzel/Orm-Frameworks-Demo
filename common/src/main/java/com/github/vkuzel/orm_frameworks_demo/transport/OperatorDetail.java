package com.github.vkuzel.orm_frameworks_demo.transport;

import com.fasterxml.jackson.databind.JsonNode;

public interface OperatorDetail {

    Long getId();

    void setId(Long id);

    JsonNode getName();

    void setName(JsonNode name);
}
