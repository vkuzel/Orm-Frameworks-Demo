package com.github.vkuzel.orm_frameworks_demo.datanucleus.audit;

import com.github.vkuzel.orm_frameworks_demo.service.UsernameProvider;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class EntityAuditor {

    @PrePersist
    public void onCreate(Auditable entity) {
        entity.setCreatedAt(LocalDateTime.now());
        entity.setCreatedBy(UsernameProvider.getUsername());
    }

    @PreUpdate
    public void onUpdate(Auditable entity) {
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setUpdatedBy(UsernameProvider.getUsername());
    }
}
