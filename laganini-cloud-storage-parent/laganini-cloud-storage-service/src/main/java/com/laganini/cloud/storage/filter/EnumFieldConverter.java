package com.laganini.cloud.storage.filter;

import com.laganini.cloud.storage.converter.Encodable;

public class EnumFieldConverter<E extends Enum<E> & Encodable<?>>
        implements AbstractSearchCriteriaMapper.FieldConverter<String, E>
{

    private final Class<E> clazz;

    public EnumFieldConverter(Class<E> clazz) {
        this.clazz = clazz;
    }

    @Override
    public E convert(String source) {
        return Encodable.enumFromString(clazz, source);
    }

}
