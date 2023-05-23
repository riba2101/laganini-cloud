package org.laganini.cloud.data.jpa.converter;

import jakarta.persistence.AttributeConverter;
import org.laganini.cloud.data.converter.AbstractEnumConverter;
import org.laganini.cloud.data.converter.Encodable;

public abstract class AbstractJpaEnumConverter<E extends Enum<E> & Encodable<ID>, ID>
        extends AbstractEnumConverter<E, ID>
        implements AttributeConverter<E, ID>
{

    protected AbstractJpaEnumConverter(Class<E> clazz) {
        super(clazz);
    }

}
