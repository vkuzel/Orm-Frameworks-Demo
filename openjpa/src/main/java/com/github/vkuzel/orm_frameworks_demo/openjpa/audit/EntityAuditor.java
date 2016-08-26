package com.github.vkuzel.orm_frameworks_demo.openjpa.audit;

import com.github.vkuzel.orm_frameworks_demo.service.UsernameProvider;
import org.apache.openjpa.audit.Auditor;
import org.apache.openjpa.kernel.Audited;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.lib.conf.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Collection;

public class EntityAuditor implements Auditor {
    @Override
    public void audit(Broker broker, Collection<Audited> newObjects, Collection<Audited> updates, Collection<Audited> deletes) {
        newObjects.forEach(audited -> addOnCreateInfo(audited.getManagedObject()));
        updates.forEach(audited -> addOnUpdateAuditInfo(audited.getManagedObject()));
    }

    private void addOnCreateInfo(Object entity) {
        for (Method method : entity.getClass().getDeclaredMethods()) {
            try {
                switch (method.getName()) {
                    case "setCreatedAt":
                        method.invoke(entity, LocalDateTime.now());
                        break;
                    case "setCreatedBy":
                        method.invoke(entity, UsernameProvider.getUsername());
                        break;
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    private void addOnUpdateAuditInfo(Object entity) {
        for (Method method : entity.getClass().getDeclaredMethods()) {
            try {
                switch (method.getName()) {
                    case "setUpdatedAt":
                        method.invoke(entity, LocalDateTime.now());
                        break;
                    case "setUpdatedBy":
                        method.invoke(entity, UsernameProvider.getUsername());
                        break;
                }
            } catch (IllegalAccessException | InvocationTargetException/* | NoSuchMethodException*/ e) {
                throw new IllegalArgumentException(e);
            }
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
