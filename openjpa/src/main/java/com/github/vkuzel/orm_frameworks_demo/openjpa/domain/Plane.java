package com.github.vkuzel.orm_frameworks_demo.openjpa.domain;

import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneDimensions;
import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneType;
import com.github.vkuzel.orm_frameworks_demo.transport.PlaneDetail;
import org.apache.openjpa.audit.Auditable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Auditable
@Entity(name = "planes")
public class Plane implements PlaneDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "planes_seq_gen")
    @SequenceGenerator(name = "planes_seq_gen", sequenceName = "planes_id_seq")
    private Long id;
    private String name;
    private DetailPlaneDimensions dimensions;
    @Column(name = "plane_type") // http://stackoverflow.com/questions/21666016/jpa-2-1-custom-naming-strategy
    private DetailPlaneType planeType;
    @Column(name = "seats_layout")
    private Integer[] seatsLayout;
    private LocalDateTime createdAt;
    @Column(name = "created_by")
    private String createdBy;
    private LocalDateTime updatedAt;
    @Column(name = "updated_by")
    private String updatedBy;
    @Version
    private int revision;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DetailPlaneDimensions getDimensions() {
        return dimensions;
    }

    public void setDimensions(DetailPlaneDimensions dimensions) {
        this.dimensions = dimensions;
    }

    public DetailPlaneType getPlaneType() {
        return planeType;
    }

    public void setPlaneType(DetailPlaneType planeType) {
        this.planeType = planeType;
    }

    public Integer[] getSeatsLayout() {
        return seatsLayout;
    }

    public void setSeatsLayout(Integer[] seatsLayout) {
        this.seatsLayout = seatsLayout;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }
}
