package com.github.vkuzel.orm_frameworks_demo.transport;

import java.time.LocalDateTime;

public interface PlaneDetail {

    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);

    DetailPlaneDimensions getDimensions();

    void setDimensions(DetailPlaneDimensions dimensions);

    DetailPlaneType getPlaneType();

    void setPlaneType(DetailPlaneType planeType);

    Integer[] getSeatsLayout();

    void setSeatsLayout(Integer[] seatsLayout);

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
