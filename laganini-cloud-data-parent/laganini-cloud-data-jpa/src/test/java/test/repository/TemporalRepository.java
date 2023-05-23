package test.repository;

import com.infobip.spring.data.jpa.ExtendedQuerydslJpaRepository;

public interface TemporalRepository
        extends ExtendedQuerydslJpaRepository<TemporalEntity, Long>
{

}
