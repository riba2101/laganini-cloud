package test.repository;

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository;

public interface OwnedRepository
        extends QuerydslR2dbcRepository<OwnedEntity, Integer>
{

}
