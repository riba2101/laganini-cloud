package org.laganini.cloud.storage.audit.provider.jpa;

import org.laganini.cloud.storage.audit.provider.AbstractRevisionServiceTest;
import org.laganini.cloud.storage.audit.provider.TestcontainersContextInitializer;
import org.laganini.cloud.storage.audit.provider.jpa.converter.JpaRevisionConverter;
import org.laganini.cloud.storage.audit.provider.jpa.entity.JpaRevision;
import org.laganini.cloud.storage.audit.utils.AuditedUtils;
import org.laganini.cloud.test.suite.IntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@IntegrationTest
@ContextConfiguration(initializers = TestcontainersContextInitializer.class, classes = JpaAuditTestConfiguration.class)
public class JpaRevisionServiceTest extends AbstractRevisionServiceTest<JpaRevision> {

    @Autowired
    private JpaRevisionRepository revisionRepository;

    @Override
    protected JpaRevision givenRevision(String name, long id, String type) {
        return new JpaRevision(AuditedUtils.buildKey(name, id), type);
    }

    @Override
    protected JpaRevisionRepository getRevisionRepository() {
        return revisionRepository;
    }

    @Override
    protected JpaRevisionService getRevisionService() {
        return new JpaRevisionService(revisionRepository, new JpaRevisionConverter());
    }

}