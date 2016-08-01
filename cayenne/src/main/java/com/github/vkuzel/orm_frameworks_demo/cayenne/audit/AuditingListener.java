package com.github.vkuzel.orm_frameworks_demo.cayenne.audit;

import com.github.vkuzel.orm_frameworks_demo.service.UsernameProvider;
import org.apache.cayenne.Persistent;
import org.apache.cayenne.annotation.PostAdd;
import org.apache.cayenne.annotation.PreUpdate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

public class AuditingListener {

    @PostAdd(entityAnnotations = Auditable.class)
    public void postAdd(Persistent object) {
        for (Method method : object.getClass().getMethods()) {
            try {
                switch (method.getName()) {
                    case "setCreatedAt":
                        method.invoke(object, LocalDateTime.now());
                        break;
                    case "setCreatedBy":
                        method.invoke(object, UsernameProvider.getUsername());
                        break;
                    case "setRevision":
                        method.invoke(object, 1);
                        break;
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    @PreUpdate(entityAnnotations = Auditable.class)
    public void preUpdate(Persistent object) {
        for (Method method : object.getClass().getMethods()) {
            try {
                switch (method.getName()) {
                    case "setUpdatedAt":
                        method.invoke(object, LocalDateTime.now());
                        break;
                    case "setUpdatedBy":
                        method.invoke(object, UsernameProvider.getUsername());
                        break;
                    case "setRevision":
                        Method getRevisionMethod = object.getClass().getMethod("getRevision");
                        int revision = (int) getRevisionMethod.invoke(object);
                        method.invoke(object, revision + 1);
                        break;
                }
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }
}
