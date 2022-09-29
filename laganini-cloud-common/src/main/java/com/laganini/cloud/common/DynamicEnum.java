package com.laganini.cloud.common;

import java.io.Serializable;

public abstract class DynamicEnum<E extends DynamicEnum<E>>
        implements Comparable<E>, Serializable {
}
