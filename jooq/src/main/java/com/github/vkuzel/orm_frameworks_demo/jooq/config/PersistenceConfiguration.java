package com.github.vkuzel.orm_frameworks_demo.jooq.config;

import com.github.vkuzel.orm_frameworks_demo.jooq.tables.records.PlanesRecord;
import com.github.vkuzel.orm_frameworks_demo.service.UsernameProvider;
import org.jooq.Record;
import org.jooq.RecordContext;
import org.jooq.RecordListenerProvider;
import org.jooq.impl.DefaultRecordListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Configuration
public class PersistenceConfiguration {

    @Bean
    public RecordListenerProvider recordListenerProvider() {
        return () -> new DefaultRecordListener() {
            @Override
            public void insertStart(RecordContext ctx) {
                if (ctx.record() instanceof PlanesRecord) {
                    Record record = ctx.record();
                    for (Method method : record.getClass().getDeclaredMethods()) {
                        try {
                            switch (method.getName()) {
                                case "setCreatedAt":
                                    method.invoke(record, LocalDateTime.now());
                                    break;
                                case "setCreatedBy":
                                    method.invoke(record, UsernameProvider.getUsername());
                                    break;
                                case "setRevision":
                                    method.invoke(record, 1);
                                    break;
                            }
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new IllegalArgumentException(e);
                        }
                    }
                }
            }

            @Override
            public void updateStart(RecordContext ctx) {
                if (ctx.record() instanceof PlanesRecord) {
                    Record record = ctx.record();
                    for (Method method : record.getClass().getDeclaredMethods()) {
                        try {
                            switch (method.getName()) {
                                case "setUpdatedAt":
                                    method.invoke(record, LocalDateTime.now());
                                    break;
                                case "setUpdatedBy":
                                    method.invoke(record, UsernameProvider.getUsername());
                                    break;
                                case "setRevision":
                                    Method getRevisionMethod = record.getClass().getDeclaredMethod("getRevision");
                                    int revision = (int) getRevisionMethod.invoke(record);
                                    method.invoke(record, revision + 1);
                                    break;
                            }
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                            throw new IllegalArgumentException(e);
                        }
                    }
                }
            }
        };
    }
}
