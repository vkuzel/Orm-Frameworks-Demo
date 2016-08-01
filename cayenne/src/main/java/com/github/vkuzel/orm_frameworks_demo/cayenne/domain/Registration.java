package com.github.vkuzel.orm_frameworks_demo.cayenne.domain;

import com.github.vkuzel.orm_frameworks_demo.cayenne.domain.auto._Registration;
import com.github.vkuzel.orm_frameworks_demo.transport.RegistrationDetail;
import org.apache.cayenne.ObjectId;

public class Registration extends _Registration implements RegistrationDetail {

    private static final long serialVersionUID = 1L;

    @Override
    public Long getId() {
        return (Long) this.getObjectId().getIdSnapshot().get("id");
    }

    @Override
    public void setId(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getPlaneId() {
        return this.getPlane().getId();
    }

    @Override
    public void setPlaneId(long planeId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getOperatorId() {
        return this.getOperator().getId();
    }

    @Override
    public void setOperatorId(long operatorId) {
        throw new UnsupportedOperationException();
    }
}
