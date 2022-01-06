package io.dailyworker.flight.enumerate.converter;

import javax.persistence.AttributeConverter;
import java.util.EnumSet;

public class EnumConverter<E extends Enum<E> & EnumCodeDelegator> implements AttributeConverter <E, String> {
    private final Class<E> clazz;

    public EnumConverter(Class<E> enumClass) {
        this.clazz = enumClass;
    }

    @Override
    public String convertToDatabaseColumn(E attribute) {
        return attribute.getCode();
    }

    @Override
    public E convertToEntityAttribute(String dbData) {
        return EnumSet.allOf(clazz)
                .stream()
                .filter(enumerate -> enumerate.getCode().equals(dbData))
                .findAny()
                .orElseThrow(NoSuchMethodError::new);
    }
}
