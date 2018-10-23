package com.github.vkuzel.orm_frameworks_demo.ebean.domain;

import com.github.vkuzel.orm_frameworks_demo.transport.RegistrationDetail;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "registrations")
public class Registration extends Model implements RegistrationDetail {

    @Id
    private Long id;
    private long planeId;
    private long operatorId;
    private String registrationNumber;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public long getPlaneId() {
        return planeId;
    }

    @Override
    public void setPlaneId(long planeId) {
        this.planeId = planeId;
    }

    @Override
    public long getOperatorId() {
        return operatorId;
    }

    @Override
    public void setOperatorId(long operatorId) {
        this.operatorId = operatorId;
    }

    @Override
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    @Override
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}
