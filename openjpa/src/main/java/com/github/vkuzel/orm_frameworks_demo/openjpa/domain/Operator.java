package com.github.vkuzel.orm_frameworks_demo.openjpa.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.vkuzel.orm_frameworks_demo.transport.OperatorDetail;

import javax.persistence.*;

@Entity(name = "operators")
public class Operator implements OperatorDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operators_seq_gen")
    @SequenceGenerator(name = "operators_seq_gen", sequenceName = "operators_id_seq")
    private Long id;
    @Column(name = "name")
    private SerializableJson nameWrapper;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JsonNode getName() {
        return nameWrapper.toJsonNode();
    }

    public void setName(JsonNode name) {
        this.nameWrapper = SerializableJson.fromJsonNode(name);
    }

    public SerializableJson getNameWrapper() {
        return nameWrapper;
    }

    public void setNameWrapper(SerializableJson nameWrapper) {
        this.nameWrapper = nameWrapper;
    }
}
