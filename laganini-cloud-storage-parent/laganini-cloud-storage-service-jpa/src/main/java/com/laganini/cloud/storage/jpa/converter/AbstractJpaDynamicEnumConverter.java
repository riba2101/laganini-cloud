package org.laganini.cloud.storage.jpa.converter;

import org.laganini.cloud.common.DynamicEnum;
import org.laganini.cloud.storage.converter.AbstractDynamicEnumConverter;
import org.laganini.cloud.storage.converter.Encodable;

import javax.persistence.AttributeConverter;

public abstract class AbstractJpaDynamicEnumConverter<E extends DynamicEnum<E> & Encodable<ID>, ID>
        extends AbstractDynamicEnumConverter<E, ID>
        implements AttributeConverter<E, ID>
{

    protected AbstractJpaDynamicEnumConverter(Class<E> clazz) {
        super(clazz);
    }

}
