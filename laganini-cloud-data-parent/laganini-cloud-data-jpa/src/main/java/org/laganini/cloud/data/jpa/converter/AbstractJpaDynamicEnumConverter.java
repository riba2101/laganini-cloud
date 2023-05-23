package org.laganini.cloud.data.jpa.converter;

import jakarta.persistence.AttributeConverter;
import org.laganini.cloud.common.DynamicEnum;
import org.laganini.cloud.data.converter.AbstractDynamicEnumConverter;
import org.laganini.cloud.data.converter.Encodable;

public abstract class AbstractJpaDynamicEnumConverter<E extends DynamicEnum<E> & Encodable<ID>, ID>
        extends AbstractDynamicEnumConverter<E, ID>
        implements AttributeConverter<E, ID>
{

    protected AbstractJpaDynamicEnumConverter(Class<E> clazz) {
        super(clazz);
    }

}
