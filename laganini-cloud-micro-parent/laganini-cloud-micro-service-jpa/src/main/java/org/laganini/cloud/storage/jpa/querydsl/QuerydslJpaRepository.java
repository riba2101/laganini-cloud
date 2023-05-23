package org.laganini.cloud.storage.jpa.querydsl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface QuerydslJpaRepository<T, ID> extends JpaRepository<T, ID>, QuerydslJpaFragment {

}
