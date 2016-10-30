package com.github.vkuzel.orm_frameworks_demo.activejpa.converter;

import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneDimensions;
import org.postgresql.util.PGobject;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Converter(autoApply = true)
public class PlaneDimensionsConverter implements AttributeConverter<DetailPlaneDimensions, PGobject> {

    private static final Pattern PG_STRING_PATTERN = Pattern.compile("^\\(([0-9.]+),([0-9.]+),([0-9.]+)\\)$");

    @Override
    public PGobject convertToDatabaseColumn(DetailPlaneDimensions detailPlaneDimensions) {
        if (detailPlaneDimensions == null) {
            return null;
        }

        try {
            PGobject pGobject = new PGobject();
            pGobject.setType("plane_dimensions");
            pGobject.setValue(toPgString(detailPlaneDimensions));
            return pGobject;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public DetailPlaneDimensions convertToEntityAttribute(PGobject pGobject) {
        if (pGobject == null) {
            return null;
        }

        return fromPgString(pGobject.getValue());
    }

    private DetailPlaneDimensions fromPgString(String pgString) {
        Matcher matcher = PG_STRING_PATTERN.matcher(pgString);
        if (matcher.find()) {
            return new DetailPlaneDimensions(
                    new BigDecimal(matcher.group(1)),
                    new BigDecimal(matcher.group(2)),
                    new BigDecimal(matcher.group(3))
            );
        } else {
            throw new IllegalArgumentException(pgString);
        }
    }

    private String toPgString(DetailPlaneDimensions dimensions) {
        return String.format("(%f,%f,%f)", dimensions.getLengthMeters(), dimensions.getWingspanMeters(), dimensions.getHeightMeters());
    }
}
