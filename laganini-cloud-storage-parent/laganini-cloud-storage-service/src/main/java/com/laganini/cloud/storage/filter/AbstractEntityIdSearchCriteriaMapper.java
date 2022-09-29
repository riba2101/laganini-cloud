package com.laganini.cloud.storage.filter;

import com.laganini.cloud.storage.entity.IdentityEntity;
import com.querydsl.core.types.dsl.EntityPathBase;

public abstract class AbstractEntityIdSearchCriteriaMapper<ENTITY extends EntityPathBase<? extends IdentityEntity<?>>>
        extends AbstractSearchCriteriaMapper
{

}
