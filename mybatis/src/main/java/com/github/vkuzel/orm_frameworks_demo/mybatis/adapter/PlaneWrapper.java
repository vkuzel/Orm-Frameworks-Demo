package com.github.vkuzel.orm_frameworks_demo.mybatis.adapter;

import com.github.vkuzel.orm_frameworks_demo.mybatis.model.Plane;
import com.github.vkuzel.orm_frameworks_demo.transport.PlaneDetail;

public class PlaneWrapper extends Plane implements PlaneDetail {
    public PlaneWrapper() {
    }

    public PlaneWrapper(Plane plane) {
        setId(plane.getId());
        setName(plane.getName());
        setPlaneType(plane.getPlaneType());
        setSeatsLayout(plane.getSeatsLayout());
        setCreatedAt(plane.getCreatedAt());
        setCreatedBy(plane.getCreatedBy());
        setUpdatedAt(plane.getUpdatedAt());
        setUpdatedBy(plane.getUpdatedBy());
        setRevision(plane.getRevision());
    }
}
