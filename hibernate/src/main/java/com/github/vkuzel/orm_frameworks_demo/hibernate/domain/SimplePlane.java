package com.github.vkuzel.orm_frameworks_demo.hibernate.domain;

import com.github.vkuzel.orm_frameworks_demo.transport.PlaneSimpleDetail;

public class SimplePlane implements PlaneSimpleDetail {

    private final long id;
    private final String name;

    public SimplePlane(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
