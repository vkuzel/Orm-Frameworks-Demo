package com.github.vkuzel.orm_frameworks_demo.activejdbc.model;

import com.github.vkuzel.orm_frameworks_demo.transport.RegistrationDetail;
import org.javalite.activejdbc.Model;

public class Registration extends Model {

    private RegistrationAdapter adapter = new RegistrationAdapter();

    public RegistrationDetail toRegistrationDetail() {
        return adapter;
    }

    public class RegistrationAdapter implements RegistrationDetail {

        private RegistrationAdapter() {
        }

        public Registration toRegistration() {
            return Registration.this;
        }

        @Override
        public Long getId() {
            return getLongId();
        }

        @Override
        public void setId(Long id) {
            Registration.this.setId(id);
        }

        @Override
        public long getPlaneId() {
            return getLong("plane_id");
        }

        @Override
        public void setPlaneId(long planeId) {
            setLong("plane_id", planeId);
        }

        @Override
        public long getOperatorId() {
            return getLong("operator_id");
        }

        @Override
        public void setOperatorId(long operatorId) {
            setLong("operator_id", operatorId);
        }

        @Override
        public String getRegistrationNumber() {
            return getString("registration_number");
        }

        @Override
        public void setRegistrationNumber(String registrationNumber) {
            setString("registration_number", registrationNumber);
        }
    }
}
