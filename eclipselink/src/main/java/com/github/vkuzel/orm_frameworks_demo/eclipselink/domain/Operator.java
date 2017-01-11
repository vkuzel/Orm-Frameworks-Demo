package com.github.vkuzel.orm_frameworks_demo.eclipselink.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.vkuzel.orm_frameworks_demo.transport.OperatorDetail;

import javax.persistence.*;

@Entity
@Table(name = "operators")
@NamedNativeQueries({
        @NamedNativeQuery(name = "findOperatorByName", query = "SELECT * FROM operators WHERE name ->> ?1 = ?2", resultClass = Operator.class),
        @NamedNativeQuery(name = "findAllOperatorsOrderByNameDesc", query = "SELECT * FROM operators ORDER BY name ->> ?1 DESC LIMIT ?2 OFFSET ?3", resultClass = Operator.class),
        @NamedNativeQuery(name = "findAllOperatorsOrderByNameAsc", query = "SELECT * FROM operators ORDER BY name ->> ?1 ASC LIMIT ?2 OFFSET ?3", resultClass = Operator.class)
})
public class Operator implements OperatorDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operators_seq_gen")
    @SequenceGenerator(name = "operators_seq_gen", sequenceName = "operators_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;
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
