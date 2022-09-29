package com.laganini.cloud.storage.jpa.converter;

import com.laganini.cloud.storage.converter.AbstractEnumConverter;
import com.laganini.cloud.storage.converter.Encodable;

import javax.persistence.AttributeConverter;

public abstract class AbstractJpaEnumConverter<E extends Enum<E> & Encodable<ID>, ID>
        extends AbstractEnumConverter<E, ID>
        implements AttributeConverter<E, ID>
{

    protected AbstractJpaEnumConverter(Class<E> clazz) {
        super(clazz);
    }

}
