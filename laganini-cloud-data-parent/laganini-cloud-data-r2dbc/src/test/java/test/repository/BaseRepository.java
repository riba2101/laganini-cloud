package test.repository;

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository;

public interface BaseRepository
        extends QuerydslR2dbcRepository<BaseEntity, Integer>
{

}
