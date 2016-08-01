package com.github.vkuzel.orm_frameworks_demo.cayenne.domain;

import com.github.vkuzel.orm_frameworks_demo.cayenne.domain.auto._Operator;
import com.github.vkuzel.orm_frameworks_demo.transport.OperatorDetail;
import org.apache.cayenne.ObjectId;

public class Operator extends _Operator implements OperatorDetail {

    private static final long serialVersionUID = 1L; 

    @Override
    public Long getId() {
        return (Long) this.getObjectId().getIdSnapshot().get("id");
    }

    @Override
    public void setId(Long id) {
        throw new UnsupportedOperationException();
    }
}
