package com.github.vkuzel.orm_frameworks_demo.hibernate.domain;

import com.github.vkuzel.orm_frameworks_demo.transport.RegistrationDetail;

import javax.persistence.*;

@Entity(name = "registrations")
@NamedStoredProcedureQuery(name = "registerNewPlane", procedureName = "register_new_plane", parameters = {
        @StoredProcedureParameter(name = "plane_id", type = Long.class, mode = ParameterMode.IN),
        @StoredProcedureParameter(name = "operator_id", type = Long.class, mode = ParameterMode.IN),
        @StoredProcedureParameter(name = "registration_number", type = String.class, mode = ParameterMode.IN),
        @StoredProcedureParameter(name = "registration_id", type = Long.class, mode = ParameterMode.OUT)
})
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
