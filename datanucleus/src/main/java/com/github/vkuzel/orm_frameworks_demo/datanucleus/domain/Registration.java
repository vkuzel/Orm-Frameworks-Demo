package com.github.vkuzel.orm_frameworks_demo.datanucleus.domain;

import com.github.vkuzel.orm_frameworks_demo.transport.RegistrationDetail;

import javax.persistence.*;

@Entity
@Table(name = "registrations")
// DataNucleus does not allow to call Postgre's stored procedure in a
// traditional way. So it's necessary to call it as a select query.
@NamedNativeQuery(name = "registerNewPlane", query = "SELECT register_new_plane(?1, ?2, ?3)")
public class Registration implements RegistrationDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "registrations_seq_gen")
    @SequenceGenerator(name = "registrations_seq_gen", sequenceName = "registrations_id_seq")
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
