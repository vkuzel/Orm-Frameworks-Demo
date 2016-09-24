package com.github.vkuzel.orm_frameworks_demo.eclipselink.audit;

import java.time.LocalDateTime;

public interface Auditable {
    void setCreatedAt(LocalDateTime createdAt);

    void setCreatedBy(String createdBy);

    void setUpdatedAt(LocalDateTime updatedAt);

    void setUpdatedBy(String updatedBy);
}
