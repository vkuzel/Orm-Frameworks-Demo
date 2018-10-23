package com.github.vkuzel.orm_frameworks_demo.ebean.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.vkuzel.orm_frameworks_demo.transport.OperatorDetail;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "operators")
public class Operator extends Model implements OperatorDetail {

    @Id
    private Long id;
    private JsonNode name;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public JsonNode getName() {
        return name;
    }

    @Override
    public void setName(JsonNode name) {
        this.name = name;
    }
}
