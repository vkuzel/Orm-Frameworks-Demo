package com.github.vkuzel.orm_frameworks_demo.datanucleus.domain;

import com.github.vkuzel.orm_frameworks_demo.datanucleus.audit.Auditable;
import com.github.vkuzel.orm_frameworks_demo.datanucleus.audit.EntityAuditor;
import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneDimensions;
import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneType;
import com.github.vkuzel.orm_frameworks_demo.transport.PlaneDetail;
import org.datanucleus.api.jpa.annotations.Extension;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "planes")
@EntityListeners({EntityAuditor.class})
public class Plane implements PlaneDetail, Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "planes_seq_gen")
    @SequenceGenerator(name = "planes_seq_gen", sequenceName = "planes_id_seq")
    private Long id;
    private String name;
    private DetailPlaneDimensions dimensions;
    private DetailPlaneType planeType;
    // I wanted to apply this mapping implicitly by definition in plugin.xml
    // file but I was unable to override DataNucleus default array mappings.
    @Extension(key = "mapping-class", value = "com.github.vkuzel.orm_frameworks_demo.datanucleus.mapping.IntArrayMapping")
    // This annotation makes field to be part of default fetch group. This
    // means that field will be retrieved from database all the time.
    @Basic
    private Integer[] seatsLayout;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
    @Version
    private int revision;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public DetailPlaneDimensions getDimensions() {
        return dimensions;
    }

    @Override
    public void setDimensions(DetailPlaneDimensions dimensions) {
        this.dimensions = dimensions;
    }

    @Override
    public DetailPlaneType getPlaneType() {
        return planeType;
    }

    @Override
    public void setPlaneType(DetailPlaneType planeType) {
        this.planeType = planeType;
    }

    @Override
    public Integer[] getSeatsLayout() {
        return seatsLayout;
    }

    @Override
    public void setSeatsLayout(Integer[] seatsLayout) {
        this.seatsLayout = seatsLayout;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String getUpdatedBy() {
        return updatedBy;
    }

    @Override
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public int getRevision() {
        return revision;
    }

    @Override
    public void setRevision(int revision) {
        this.revision = revision;
    }
}
