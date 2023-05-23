package test.repository;

import com.infobip.spring.data.jpa.ExtendedQuerydslJpaRepository;

public interface OwnedRepository
        extends ExtendedQuerydslJpaRepository<OwnedEntity, Long>
{

}
