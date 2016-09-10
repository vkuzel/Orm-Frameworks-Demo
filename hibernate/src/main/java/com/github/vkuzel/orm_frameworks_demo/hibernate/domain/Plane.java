package com.github.vkuzel.orm_frameworks_demo.hibernate.domain;

import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneDimensions;
import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneType;
import com.github.vkuzel.orm_frameworks_demo.transport.PlaneDetail;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "planes")
@EntityListeners(AuditingEntityListener.class)
public class Plane implements PlaneDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "planes_seq_gen")
    @SequenceGenerator(name = "planes_seq_gen", sequenceName = "planes_id_seq")
    private Long id;
    private String name;
    @Type(type = "com.github.vkuzel.orm_frameworks_demo.hibernate.mapping.PlaneDimensionsMapping")
    private DetailPlaneDimensions dimensions;
    @Type(type = "com.github.vkuzel.orm_frameworks_demo.hibernate.mapping.PlaneTypeMapping")
    private DetailPlaneType planeType;
    @Type(type = "com.github.vkuzel.orm_frameworks_demo.hibernate.mapping.IntArrayMapping")
    private Integer[] seatsLayout;
    @CreatedDate
    private LocalDateTime createdAt;
    @CreatedBy
    private String createdBy;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @LastModifiedBy
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
