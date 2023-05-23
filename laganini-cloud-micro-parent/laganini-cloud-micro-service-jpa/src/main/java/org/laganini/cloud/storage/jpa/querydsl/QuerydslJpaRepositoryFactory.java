package org.laganini.cloud.storage.jpa.querydsl;

import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryComposition;
import org.springframework.data.repository.core.support.RepositoryFragment;

import java.io.Serializable;

public class QuerydslJpaRepositoryFactory extends JpaRepositoryFactory {

    private final EntityManager      entityManager;
    private final EntityPathResolver entityPathResolver;
    private final JPAQueryFactory    jpaQueryFactory;

    public QuerydslJpaRepositoryFactory(
            EntityManager entityManager,
            EntityPathResolver entityPathResolver,
            JPAQueryFactory jpaQueryFactory
    )
    {
        super(entityManager);
        this.entityManager = entityManager;
        this.entityPathResolver = entityPathResolver;
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    protected RepositoryComposition.RepositoryFragments getRepositoryFragments(RepositoryMetadata metadata) {
        RepositoryComposition.RepositoryFragments fragments         = super.getRepositoryFragments(metadata);
        JpaEntityInformation<?, Serializable>     entityInformation = getEntityInformation(metadata.getDomainType());
        EntityPath<?>                             path              = entityPathResolver.createPath(entityInformation.getJavaType());
        DefaultQuerydslJpaFragment<?> defaultQuerydslJpaFragment = instantiateClass(
                DefaultQuerydslJpaFragment.class,
                path,
                jpaQueryFactory,
                entityManager
        );
        RepositoryFragment<DefaultQuerydslJpaFragment<?>> fragment = RepositoryFragment.implemented(
                defaultQuerydslJpaFragment);
        return fragments.append(fragment);
    }
}
