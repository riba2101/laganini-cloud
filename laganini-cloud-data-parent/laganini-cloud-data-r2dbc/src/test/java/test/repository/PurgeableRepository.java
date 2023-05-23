package test.repository;

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository;

public interface PurgeableRepository
        extends QuerydslR2dbcRepository<PurgeableEntity, Integer>
{

}
