package test.repository;

import com.infobip.spring.data.jpa.ExtendedQuerydslJpaRepository;

public interface PurgeableRepository
        extends ExtendedQuerydslJpaRepository<PurgeableEntity, Long>
{

}
