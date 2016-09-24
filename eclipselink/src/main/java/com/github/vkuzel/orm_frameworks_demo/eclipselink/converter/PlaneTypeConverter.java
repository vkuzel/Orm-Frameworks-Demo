package com.github.vkuzel.orm_frameworks_demo.eclipselink.converter;

import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneType;
import org.postgresql.util.PGobject;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.SQLException;

@Converter(autoApply = true)
public class PlaneTypeConverter implements AttributeConverter<DetailPlaneType, PGobject> {
    @Override
    public PGobject convertToDatabaseColumn(DetailPlaneType detailPlaneType) {
        if (detailPlaneType == null) {
            return null;
        }

        try {
            PGobject pGobject = new PGobject();
            pGobject.setType("plane_type");
            pGobject.setValue(detailPlaneType.name());
            return pGobject;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public DetailPlaneType convertToEntityAttribute(PGobject pGobject) {
        if (pGobject == null) {
            return null;
        }

        return DetailPlaneType.valueOf(pGobject.getValue());
    }
}
