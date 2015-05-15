package ru.shashki.server.util.converter;

import ru.shashki.server.entity.ShashistStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 19.04.15
 * Time: 19:38
 */
@Converter(autoApply = true)
public class ShashistStatusConverter implements AttributeConverter<ShashistStatus, String> {

    @Override
    public String convertToDatabaseColumn(ShashistStatus attribute) {
        if (attribute == null) {
            return "";
        }
        return attribute.getDbRepresentation();
    }

    @Override
    public ShashistStatus convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        for (ShashistStatus shashistStatus : ShashistStatus.values()) {
            if (dbData.equals(shashistStatus.getDbRepresentation())) {
                return shashistStatus;
            }
        }
        throw new IllegalArgumentException("Unknown attribute value " + dbData);
    }
}
