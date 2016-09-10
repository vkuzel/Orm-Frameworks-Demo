package com.github.vkuzel.orm_frameworks_demo.datanucleus.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.vkuzel.orm_frameworks_demo.datanucleus.mapping.JsonRDBMSMapping;
import com.github.vkuzel.orm_frameworks_demo.transport.OperatorDetail;
import org.postgresql.util.PGobject;

import javax.persistence.*;

@Entity
@Table(name = "operators")
@NamedNativeQueries({
        @NamedNativeQuery(name = "findOperatorByName", query = "SELECT * FROM operators WHERE name ->> ?1 = ?2"),
        @NamedNativeQuery(name = "findAllOperatorsOrderByNameDesc", query = "SELECT * FROM operators ORDER BY name ->> ?1 DESC LIMIT ?2 OFFSET ?3"),
        @NamedNativeQuery(name = "findAllOperatorsOrderByNameAsc", query = "SELECT * FROM operators ORDER BY name ->> ?1 ASC LIMIT ?2 OFFSET ?3")
})
public class Operator implements OperatorDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operators_seq_gen")
    @SequenceGenerator(name = "operators_seq_gen", sequenceName = "operators_id_seq")
    private Long id;
    private JsonNode name;

    public Operator() {
    }

    // This parameterized constructor is used by native queries. Unfortunately
    // it seems like Data Nucleus does not use same mapping methods when
    // results from native queries are returned.
    public Operator(Long id, PGobject name) {
        this.id = id;
        this.name = JsonRDBMSMapping.fromPGObject(name);
    }

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
