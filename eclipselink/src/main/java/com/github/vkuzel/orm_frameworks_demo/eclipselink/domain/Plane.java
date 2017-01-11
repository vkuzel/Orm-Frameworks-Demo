package com.github.vkuzel.orm_frameworks_demo.eclipselink.domain;

import com.github.vkuzel.orm_frameworks_demo.eclipselink.audit.Auditable;
import com.github.vkuzel.orm_frameworks_demo.eclipselink.audit.EntityAuditor;
import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneDimensions;
import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneType;
import com.github.vkuzel.orm_frameworks_demo.transport.PlaneDetail;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "planes")
@EntityListeners({EntityAuditor.class})
public class Plane implements PlaneDetail, Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "planes_seq_gen")
    @SequenceGenerator(name = "planes_seq_gen", sequenceName = "planes_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;
    private String name;
    private DetailPlaneDimensions dimensions;
    // You can implement your own naming strategy via SessionCustomizer. But it
    // seems a little bit strange to me so I decided to use column annotation
    // in this example. See http://stackoverflow.com/questions/15441931/eclipselink-custom-table-and-column-naming-strategy
    @Column(name = "plane_type")
    private DetailPlaneType planeType;
    @Column(name="seats_layout")
    private Integer[] seatsLayout;
    @CreatedDate
    @Column(name="created_at")
    private LocalDateTime createdAt;
    @Column(name="created_by")
    private String createdBy;
    @Column(name="updated_at")
    private LocalDateTime updatedAt;
    @Column(name="updated_by")
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
