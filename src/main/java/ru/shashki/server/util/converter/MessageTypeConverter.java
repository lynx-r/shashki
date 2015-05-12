package ru.shashki.server.util.converter;

import ru.shashki.server.entity.MessageType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 19.04.15
 * Time: 19:38
 */
@Converter(autoApply = true)
public class MessageTypeConverter implements AttributeConverter<MessageType, String> {

    @Override
    public String convertToDatabaseColumn(MessageType attribute) {
        if (attribute == null) {
            return "";
        }
        return attribute.getDbRepresentation();
    }

    @Override
    public MessageType convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        for (MessageType messageType : MessageType.values()) {
            if (dbData.equals(messageType.getDbRepresentation())) {
                return messageType;
            }
        }
        throw new IllegalArgumentException("Unknown attribute value " + dbData);
    }
}
