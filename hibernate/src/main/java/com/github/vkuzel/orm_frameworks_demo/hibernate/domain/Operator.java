package com.github.vkuzel.orm_frameworks_demo.hibernate.domain;

import com.github.vkuzel.orm_frameworks_demo.transport.OperatorDetail;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Map;

@Entity(name = "operators")
public class Operator implements OperatorDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operators_seq_gen")
    @SequenceGenerator(name = "operators_seq_gen", sequenceName = "operators_id_seq")
    private Long id;
    @Type(type="com.github.vkuzel.orm_frameworks_demo.hibernate.mapping.JsonMapping")
    private Map<String, String> name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, String> getName() {
        return name;
    }

    public void setName(Map<String, String> name) {
        this.name = name;
    }
}
