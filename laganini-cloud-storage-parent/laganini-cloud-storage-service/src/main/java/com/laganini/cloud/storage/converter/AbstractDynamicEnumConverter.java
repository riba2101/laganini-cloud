package org.laganini.cloud.storage.converter;

import org.laganini.cloud.common.DynamicEnum;

public abstract class AbstractDynamicEnumConverter<E extends DynamicEnum<E> & Encodable<ID>, ID> {

    private final Class<E> clazz;

    public AbstractDynamicEnumConverter(Class<E> clazz) {
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

        return Encodable.dynamicEnumFromId(clazz, id);
    }

}
