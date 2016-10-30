package com.github.vkuzel.orm_frameworks_demo.activejpa.converter;

import com.github.vkuzel.orm_frameworks_demo.activejpa.config.PersistenceConfiguration;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Array;
import java.sql.SQLException;

@Converter(autoApply = true)
@Component
public class SeatsLayoutConverter implements AttributeConverter<Integer[], Array> {

    @Override
    public Array convertToDatabaseColumn(Integer[] integers) {
        try {
            return PersistenceConfiguration.getDataSourceStatic().getConnection().createArrayOf("int", integers);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Integer[] convertToEntityAttribute(Array array) {
        if (array == null) {
            return null;
        }

        try {
            return (Integer[]) array.getArray();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
