package com.github.vkuzel.orm_frameworks_demo.eclipselink.domain;

import com.github.vkuzel.orm_frameworks_demo.transport.RegistrationDetail;

import javax.persistence.*;

@Entity
@Table(name = "registrations")
@NamedNativeQuery(name = "registerNewPlane", query = "SELECT register_new_plane(?1, ?2, ?3)")
public class Registration implements RegistrationDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "registrations_seq_gen")
    @SequenceGenerator(name = "registrations_seq_gen", sequenceName = "registrations_id_seq")
    private Long id;
    @Column(name = "plane_id")
    private long planeId;
    @Column(name = "operator_id")
    private long operatorId;
    @Column(name = "registration_number")
    private String registrationNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getPlaneId() {
        return planeId;
    }

    public void setPlaneId(long planeId) {
        this.planeId = planeId;
    }

    public long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(long operatorId) {
        this.operatorId = operatorId;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}
