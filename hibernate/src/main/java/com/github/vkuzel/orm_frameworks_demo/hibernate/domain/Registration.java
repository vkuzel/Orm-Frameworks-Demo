package com.github.vkuzel.orm_frameworks_demo.hibernate.domain;

import com.github.vkuzel.orm_frameworks_demo.transport.OperatorDetail;
import com.github.vkuzel.orm_frameworks_demo.transport.PlaneDetail;
import com.github.vkuzel.orm_frameworks_demo.transport.RegistrationDetail;

import javax.persistence.*;

@Entity(name = "registrations")
public class Registration implements RegistrationDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "registrations_seq_gen")
    @SequenceGenerator(name = "registrations_seq_gen", sequenceName = "registrations_id_seq")
    private Long id;
    @ManyToOne
    @JoinColumn(name="plane_id")
    private Plane plane;
    @ManyToOne
    @JoinColumn(name="operator_id")
    private Operator operator;
    private String registrationNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlaneDetail getPlane() {
        return plane;
    }

    public void setPlane(PlaneDetail plane) {
        this.plane = (Plane) plane;
    }

    public OperatorDetail getOperator() {
        return operator;
    }

    public void setOperator(OperatorDetail operator) {
        this.operator = (Operator) operator;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}
