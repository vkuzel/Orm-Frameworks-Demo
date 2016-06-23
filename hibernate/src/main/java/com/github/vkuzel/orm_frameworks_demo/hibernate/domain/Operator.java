package com.github.vkuzel.orm_frameworks_demo.hibernate.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.vkuzel.orm_frameworks_demo.transport.OperatorDetail;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity(name = "operators")
public class Operator implements OperatorDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operators_seq_gen")
    @SequenceGenerator(name = "operators_seq_gen", sequenceName = "operators_id_seq")
    private Long id;
    @Type(type="com.github.vkuzel.orm_frameworks_demo.hibernate.mapping.JsonMapping")
    private JsonNode name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JsonNode getName() {
        return name;
    }

    public void setName(JsonNode name) {
        this.name = name;
    }
}
