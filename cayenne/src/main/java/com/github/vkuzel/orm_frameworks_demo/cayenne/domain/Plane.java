package com.github.vkuzel.orm_frameworks_demo.cayenne.domain;

import com.github.vkuzel.orm_frameworks_demo.cayenne.audit.Auditable;
import com.github.vkuzel.orm_frameworks_demo.cayenne.domain.auto._Plane;
import com.github.vkuzel.orm_frameworks_demo.transport.PlaneDetail;
import org.apache.cayenne.ObjectId;

// This class and classes in this package has been moved here from generated
// source set. If you regenerate classes don't forget to remove generated
// subclasses in generated source set so they won't clash with classes in this
// package.
@Auditable
public class Plane extends _Plane implements PlaneDetail {

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
