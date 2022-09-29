package org.laganini.cloud.storage.filter;

import org.laganini.cloud.storage.entity.IdentityEntity;
import com.querydsl.core.types.dsl.EntityPathBase;

public abstract class AbstractEntityCompositeIdSearchCriteriaMapper<ENTITY extends EntityPathBase<? extends IdentityEntity<?>>>
        extends AbstractSearchCriteriaMapper
{

}
