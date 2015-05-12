package ru.shashki.server.util.converter;

import ru.shashki.server.entity.AuthProvider;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 11.05.15
 * Time: 20:14
 */
@Converter(autoApply = true)
public class AuthProviderConverter implements AttributeConverter<AuthProvider, String> {

    @Override
    public String convertToDatabaseColumn(AuthProvider attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getDbRepresentation();
    }

    @Override
    public AuthProvider convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        for (AuthProvider authProvider : AuthProvider.values()) {
            if (dbData.equals(authProvider.getDbRepresentation())) {
                return authProvider;
            }
        }
        throw new IllegalArgumentException("Unknown attribute value " + dbData);
    }
}
