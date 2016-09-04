package com.github.vkuzel.orm_frameworks_demo.openjpa.audit;

import com.github.vkuzel.orm_frameworks_demo.service.UsernameProvider;
import org.apache.openjpa.audit.Auditor;
import org.apache.openjpa.kernel.Audited;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.lib.conf.Configuration;

import java.time.LocalDateTime;
import java.util.Collection;

public class EntityAuditor implements Auditor {
    @Override
    public void audit(Broker broker, Collection<Audited> newObjects, Collection<Audited> updates, Collection<Audited> deletes) {
        newObjects.forEach(audited -> addOnCreateInfo(audited.getManagedObject()));
        updates.forEach(audited -> addOnUpdateAuditInfo(audited.getManagedObject()));
    }

    private void addOnCreateInfo(Object entity) {
        if (entity instanceof AuditableEntity) {
            AuditableEntity auditableEntity = (AuditableEntity) entity;
            auditableEntity.setCreatedAt(LocalDateTime.now());
            auditableEntity.setCreatedBy(UsernameProvider.getUsername());
        }
    }

    private void addOnUpdateAuditInfo(Object entity) {
        if (entity instanceof AuditableEntity) {
            AuditableEntity auditableEntity = (AuditableEntity) entity;
            auditableEntity.setUpdatedAt(LocalDateTime.now());
            auditableEntity.setUpdatedBy(UsernameProvider.getUsername());
        }
    }

    @Override
    public boolean isRollbackOnError() {
        return false;
    }

    @Override
    public void setConfiguration(Configuration conf) {

    }

    @Override
    public void startConfiguration() {

    }

    @Override
    public void endConfiguration() {

    }

    @Override
    public void close() throws Exception {

    }
}
