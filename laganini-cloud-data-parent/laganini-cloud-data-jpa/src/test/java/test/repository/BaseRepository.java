package test.repository;

import com.infobip.spring.data.jpa.ExtendedQuerydslJpaRepository;

public interface BaseRepository
        extends ExtendedQuerydslJpaRepository<BaseEntity, Long>
{

}
