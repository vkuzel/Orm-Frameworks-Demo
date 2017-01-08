package com.github.vkuzel.orm_frameworks_demo.exposed.domain;

import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneDimensions;
import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneType;
import com.github.vkuzel.orm_frameworks_demo.transport.PlaneDetail;
import org.jetbrains.exposed.sql.Column;
import org.jetbrains.exposed.sql.LongColumnType;
import org.jetbrains.exposed.sql.Table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

public class Plane implements PlaneDetail {

    public static final Table TABLE = createTable();

    private Long id;
    private String name;
    private DetailPlaneDimensions dimensions;
    private DetailPlaneType planeType;
    private Integer[] seatsLayout;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private int revision;

    public static Table createTable() {
        Table table = new Table("planes");

        Column<Integer> id = table.registerColumn("id", new LongColumnType());
        table.autoIncrement(id);
        table.primaryKey(id, 1);
        table.entityId(id);

        Column<String> name = table.varchar("name", 250, null);

        return table;
    }

    public static Optional<Plane> fromResultSet(ResultSet resultSet) {
        Plane plane = null;
        try {
            while (resultSet.next()) {
                plane = new Plane();
                plane.id = resultSet.getLong("id");
                plane.name = resultSet.getString("name");
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return Optional.ofNullable(plane);
    }

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
