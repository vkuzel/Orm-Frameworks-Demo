package com.github.vkuzel.orm_frameworks_demo.transport;

import com.github.vkuzel.orm_frameworks_demo.common.Utils;

import java.time.LocalDateTime;
import java.util.List;

public interface PlaneDetail {

    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);

    PlaneDimensions getDimensions();

    void setDimensions(PlaneDimensions dimensions);

    PlaneType getPlaneType();

    void setPlaneType(PlaneType planeType);

    List<Integer> getSeatsLayout();

    void setSeatsLayout(List<Integer> seatsLayout);

    LocalDateTime getCreatedAt();

    void setCreatedAt(LocalDateTime createdAt);

    String getCreatedBy();

    void setCreatedBy(String createdBy);

    LocalDateTime getUpdatedAt();

    void setUpdatedAt(LocalDateTime updatedAt);

    String getUpdatedBy();

    void setUpdatedBy(String updatedBy);

    int getRevision();

    void setRevision(int revision);
}
