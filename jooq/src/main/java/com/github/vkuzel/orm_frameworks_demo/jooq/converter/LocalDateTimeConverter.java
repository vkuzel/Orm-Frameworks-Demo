package com.github.vkuzel.orm_frameworks_demo.jooq.converter;

import org.jooq.Converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class LocalDateTimeConverter implements Converter<Timestamp, LocalDateTime> {

    @Override
    public LocalDateTime from(Timestamp databaseObject) {
        return databaseObject != null ? databaseObject.toLocalDateTime() : null;
    }

    @Override
    public Timestamp to(LocalDateTime userObject) {
        return userObject != null ? Timestamp.valueOf(userObject) : null;
    }

    @Override
    public Class<Timestamp> fromType() {
        return Timestamp.class;
    }

    @Override
    public Class<LocalDateTime> toType() {
        return LocalDateTime.class;
    }
}
