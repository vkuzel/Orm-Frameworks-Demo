package com.github.vkuzel.orm_frameworks_demo.openjpa.audit;

import java.time.LocalDateTime;

public interface AuditableEntity {

    void setCreatedAt(LocalDateTime createdAt);

    void setCreatedBy(String createdBy);

    void setUpdatedAt(LocalDateTime updatedAt);

    void setUpdatedBy(String updatedBy);
}
