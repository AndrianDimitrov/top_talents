package com.topTalents.topTalents.util;

import com.topTalents.topTalents.data.enums.Position;
import jakarta.persistence.AttributeConverter;

public class PositionConverter implements AttributeConverter<Position, String> {

    @Override
    public String convertToDatabaseColumn(Position position) {
        return position != null ? position.getValue() : null;
    }

    @Override
    public Position convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        for (Position position : Position.values()) {
            if (position.getValue().equals(dbData)) {
                return position;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + dbData);
    }
}
