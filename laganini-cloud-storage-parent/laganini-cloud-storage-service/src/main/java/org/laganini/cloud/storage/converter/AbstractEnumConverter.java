package org.laganini.cloud.storage.converter;

public abstract class AbstractEnumConverter<E extends Enum<E> & Encodable<ID>, ID> {

    private final Class<E> clazz;

    public AbstractEnumConverter(Class<E> clazz) {
        this.clazz = clazz;
    }

    public ID convertToDatabaseColumn(E attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getId();
    }

    public E convertToEntityAttribute(ID id) {
        if (id == null) {
            return null;
        }

        return Encodable.enumFromId(clazz, id);
    }

}
