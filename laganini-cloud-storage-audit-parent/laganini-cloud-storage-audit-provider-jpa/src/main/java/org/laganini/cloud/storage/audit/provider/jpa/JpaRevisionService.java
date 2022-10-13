package org.laganini.cloud.storage.audit.provider.jpa;

import org.laganini.cloud.storage.audit.converter.RevisionConverter;
import org.laganini.cloud.storage.audit.dto.Revision;
import org.laganini.cloud.storage.audit.provider.jpa.entity.JpaRevision;
import org.laganini.cloud.storage.audit.service.AbstractRevisionService;
import org.laganini.cloud.storage.audit.utils.AuditedUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

//@Transactional(
//        propagation = Propagation.REQUIRES_NEW,
//        transactionManager = LaganiniStorageAuditJpaProviderAutoConfiguration.TRANSACTION_MANAGER_BEAN
//)
public class JpaRevisionService extends AbstractRevisionService<JpaRevision> {

    private final JpaRevisionRepository      repository;
//    private final PlatformTransactionManager platformTransactionManager;

    public JpaRevisionService(
            JpaRevisionRepository repository,
            RevisionConverter<Revision, JpaRevision> converter/*,
            PlatformTransactionManager platformTransactionManager*/
    )
    {
        super(repository, converter);

        this.repository = repository;
//        this.platformTransactionManager = platformTransactionManager;
    }

    @Override
    public Revision create(String name, String type, Object id) {
//        DefaultTransactionAttribute definition = new DefaultTransactionAttribute();
//        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
//        TransactionStatus transaction = platformTransactionManager.getTransaction(definition);
        Revision to = converter.to(repository.saveAndFlush(converter.from(new Revision(
                AuditedUtils.buildKey(name, id),
                type
        ))));
//        platformTransactionManager.commit(transaction);
        return to;
    }

}
