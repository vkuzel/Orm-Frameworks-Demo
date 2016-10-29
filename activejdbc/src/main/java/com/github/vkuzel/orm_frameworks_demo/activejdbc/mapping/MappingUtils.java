package com.github.vkuzel.orm_frameworks_demo.activejdbc.mapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneDimensions;
import com.github.vkuzel.orm_frameworks_demo.transport.DetailPlaneType;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DBException;
import org.postgresql.jdbc.PgArray;
import org.postgresql.util.PGobject;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MappingUtils {

    private static final Pattern PLANE_DIMENSIONS_PATTERN = Pattern.compile("^\\(([0-9.]+),([0-9.]+),([0-9.]+)\\)$");
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    public static DetailPlaneDimensions toDetailPlaneDimensions(Object object) {
        PGobject pGobject = (PGobject) object;

        Matcher matcher = PLANE_DIMENSIONS_PATTERN.matcher(pGobject.getValue());
        if (matcher.find()) {
            return new DetailPlaneDimensions(
                    new BigDecimal(matcher.group(1)),
                    new BigDecimal(matcher.group(2)),
                    new BigDecimal(matcher.group(3))
            );
        } else {
            throw new IllegalArgumentException(pGobject.getValue());
        }
    }

    public static Object fromDetailPlaneDimensions(DetailPlaneDimensions dimensions) {
        if (dimensions == null) {
            return null;
        }

        try {
            String pgString = String.format("(%f,%f,%f)", dimensions.getLengthMeters(), dimensions.getWingspanMeters(), dimensions.getHeightMeters());
            PGobject pgObject = new PGobject();
            pgObject.setType("plane_dimensions");
            pgObject.setValue(pgString);
            return pgObject;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public static DetailPlaneType toDetailPlaneType(Object object) {
        if (object == null) {
            return null;
        }

        PGobject pGobject = (PGobject) object;
        return DetailPlaneType.valueOf(pGobject.getValue());
    }

    public static Object fromDetailPlaneType(DetailPlaneType planeType) {
        if (planeType == null) {
            return null;
        }

        try {
            PGobject pgObject = new PGobject();
            pgObject.setType("plane_type");
            pgObject.setValue(planeType.name());
            return pgObject;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public static Integer[] toIntegerArray(Object object) {
        if (object == null) {
            return null;
        }

        try {
            PgArray pgArray = (PgArray) object;
            return (Integer[]) pgArray.getArray();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static Object fromIntegerArray(Integer[] integerArray) {
        if (integerArray == null) {
            return null;
        }

        try {
            return Base.connection().createArrayOf("int", integerArray);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public static LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }

        return timestamp.toLocalDateTime();
    }

    public static Timestamp fromLocalDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }

        return Timestamp.valueOf(localDateTime);
    }

    public static JsonNode toJson(Object object) {
        if (object == null) {
            return null;
        }

        try {
            PGobject pGobject = (PGobject) object;
            return JSON_MAPPER.readValue(pGobject.getValue(), JsonNode.class);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static Object fromJson(JsonNode jsonNode) {
        if (jsonNode == null) {
            return null;
        }

        try {
            PGobject pGobject = new PGobject();
            pGobject.setValue(JSON_MAPPER.writeValueAsString(jsonNode));
            pGobject.setType("json");
            return pGobject;
        } catch (JsonProcessingException | SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
