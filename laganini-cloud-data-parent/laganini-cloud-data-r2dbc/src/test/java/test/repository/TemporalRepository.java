package test.repository;

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository;

public interface TemporalRepository
        extends QuerydslR2dbcRepository<TemporalEntity, Integer>
{

}
