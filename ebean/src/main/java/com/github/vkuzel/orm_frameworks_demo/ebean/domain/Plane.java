package com.github.vkuzel.orm_frameworks_demo.ebean.domain;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.*;
import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneDimensions;
import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneType;
import com.github.vkuzel.orm_frameworks_demo.transport.PlaneDetail;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Entity
// It won't be necessary to specify table's name if a custom naming convention
// would be developed. See the UnderscoreNamingConvention class.
@Table(name = "planes")
public class Plane extends Model implements PlaneDetail {

    @Id
    private Long id;
    private String name;
    private DetailPlaneDimensions dimensions;
    // I've implemented converter for following type, because I reuse the
    // enum with multiple ORM implementations. In real world it is not
    // necessary since Ebean provides handy @DbEnumValue annotation.
    //
    // @see http://ebean-orm.github.io/docs/mapping/extension/dbenumvalue
    private DetailPlaneType planeType;
    // With Ebean it's easier to map database arrays to a list type.
    @DbArray
    @Column(name = "seats_layout")
    private List<Integer> seatsLayoutList;
    @WhenCreated
    private LocalDateTime createdAt;
    @WhoCreated
    private String createdBy;
    @WhenModified
    private LocalDateTime updatedAt;
    @WhoModified
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

    public List<Integer> getSeatsLayoutList() {
        return seatsLayoutList;
    }

    public void setSeatsLayoutList(List<Integer> seatsLayoutList) {
        this.seatsLayoutList = seatsLayoutList;
    }

    public Integer[] getSeatsLayout() {
        return seatsLayoutList.toArray(new Integer[]{});
    }

    public void setSeatsLayout(Integer[] seatsLayout) {
        this.seatsLayoutList = Arrays.asList(seatsLayout);
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
