package com.github.vkuzel.orm_frameworks_demo.activejdbc.model;

import com.github.vkuzel.orm_frameworks_demo.activejdbc.audit.AuditModel;
import com.github.vkuzel.orm_frameworks_demo.activejdbc.mapping.MappingUtils;
import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneDimensions;
import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneType;
import com.github.vkuzel.orm_frameworks_demo.transport.PlaneDetail;
import org.javalite.activejdbc.annotations.VersionColumn;

import java.time.LocalDateTime;

@VersionColumn("revision")
public class Plane extends AuditModel {

    private PlaneAdapter adapter = new PlaneAdapter();

    public PlaneDetail toPlaneDetail() {
        return adapter;
    }

    public class PlaneAdapter implements PlaneDetail {

        private PlaneAdapter() {
        }

        public Plane toPlane() {
            return Plane.this;
        }

        @Override
        public Long getId() {
            return getLongId();
        }

        @Override
        public void setId(Long id) {
            Plane.this.setId(id);
        }

        @Override
        public String getName() {
            return getString("name");
        }

        @Override
        public void setName(String name) {
            setString("name", name);
        }

        @Override
        public DetailPlaneDimensions getDimensions() {
            return MappingUtils.toDetailPlaneDimensions(get("dimensions"));
        }

        @Override
        public void setDimensions(DetailPlaneDimensions dimensions) {
            set("dimensions", MappingUtils.fromDetailPlaneDimensions(dimensions));
        }

        @Override
        public DetailPlaneType getPlaneType() {
            return MappingUtils.toDetailPlaneType(get("plane_type"));
        }

        @Override
        public void setPlaneType(DetailPlaneType planeType) {
            set("plane_type", MappingUtils.fromDetailPlaneType(planeType));
        }

        @Override
        public Integer[] getSeatsLayout() {
            return MappingUtils.toIntegerArray(get("seats_layout"));
        }

        @Override
        public void setSeatsLayout(Integer[] seatsLayout) {
            set("seats_layout", MappingUtils.fromIntegerArray(seatsLayout));
        }

        @Override
        public LocalDateTime getCreatedAt() {
            return MappingUtils.toLocalDateTime(getTimestamp("created_at"));
        }

        @Override
        public void setCreatedAt(LocalDateTime createdAt) {
            setTimestamp("created_at", MappingUtils.fromLocalDateTime(createdAt));
        }

        @Override
        public String getCreatedBy() {
            return getString("created_by");
        }

        @Override
        public void setCreatedBy(String createdBy) {
            setString("created_by", createdBy);
        }

        @Override
        public LocalDateTime getUpdatedAt() {
            return MappingUtils.toLocalDateTime(getTimestamp("updated_at"));
        }

        @Override
        public void setUpdatedAt(LocalDateTime updatedAt) {
            setTimestamp("updated_at", MappingUtils.fromLocalDateTime(updatedAt));
        }

        @Override
        public String getUpdatedBy() {
            return getString("updated_by");
        }

        @Override
        public void setUpdatedBy(String updatedBy) {
            setString("updated_by", updatedBy);
        }

        @Override
        public int getRevision() {
            return getInteger("revision");
        }

        @Override
        public void setRevision(int revision) {
            setInteger("revision", revision);
        }
    }
}
