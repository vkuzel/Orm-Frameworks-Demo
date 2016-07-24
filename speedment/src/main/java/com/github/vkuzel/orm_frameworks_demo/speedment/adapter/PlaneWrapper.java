package com.github.vkuzel.orm_frameworks_demo.speedment.adapter;

import com.github.vkuzel.orm_frameworks_demo.speedment.ormframeworksdemo.public_.planes.Planes;
import com.github.vkuzel.orm_frameworks_demo.speedment.transport.PlaneDimensions;
import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneDimensions;
import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneType;
import com.github.vkuzel.orm_frameworks_demo.transport.PlaneDetail;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

public class PlaneWrapper implements PlaneDetail {

    private Planes entity;

    public PlaneWrapper(Planes entity) {
        this.entity = entity;
    }

    public Planes toEntity() {
        return entity;
    }

    @Override
    public Long getId() {
        return entity.getId();
    }

    @Override
    public void setId(Long id) {
        entity.setId(id);
    }

    @Override
    public String getName() {
        return entity.getName();
    }

    @Override
    public void setName(String name) {
        entity.setName(name);
    }

    @Override
    public DetailPlaneDimensions getDimensions() {
        PlaneDimensions dimensions = entity.getDimensions();
        return new DetailPlaneDimensions(
                dimensions.getLengthMeters(),
                dimensions.getWingspanMeters(),
                dimensions.getHeightMeters()
        );
    }

    @Override
    public void setDimensions(DetailPlaneDimensions dimensions) {
        PlaneDimensions planeDimensions = new PlaneDimensions();
        planeDimensions.setLengthMeters(dimensions.getLengthMeters());
        planeDimensions.setWingspanMeters(dimensions.getWingspanMeters());
        planeDimensions.setHeightMeters(dimensions.getHeightMeters());
        entity.setDimensions(planeDimensions);
    }

    @Override
    public DetailPlaneType getPlaneType() {
        return null;
    }

    @Override
    public void setPlaneType(DetailPlaneType planeType) {

    }

    @Override
    public Integer[] getSeatsLayout() {
        return new Integer[0];
    }

    @Override
    public void setSeatsLayout(Integer[] seatsLayout) {

    }

    @Override
    public LocalDateTime getCreatedAt() {
        return entity.getCreatedAt().toLocalDateTime();
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        entity.setCreatedAt(Timestamp.valueOf(createdAt));
    }

    @Override
    public String getCreatedBy() {
        return entity.getCreatedBy();
    }

    @Override
    public void setCreatedBy(String createdBy) {
        entity.setCreatedBy(createdBy);
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        Optional<Timestamp> updatedAt = entity.getUpdatedAt();
        return updatedAt.isPresent() ? updatedAt.get().toLocalDateTime() : null;
    }

    @Override
    public void setUpdatedAt(LocalDateTime updatedAt) {
        entity.setUpdatedAt(Timestamp.valueOf(updatedAt));
    }

    @Override
    public String getUpdatedBy() {
        Optional<String> updatedBy = entity.getUpdatedBy();
        return updatedBy.isPresent() ? updatedBy.get() : null;
    }

    @Override
    public void setUpdatedBy(String updatedBy) {
        entity.setUpdatedBy(updatedBy);
    }

    @Override
    public int getRevision() {
        return entity.getRevision();
    }

    @Override
    public void setRevision(int revision) {
        entity.setRevision(revision);
    }
}
