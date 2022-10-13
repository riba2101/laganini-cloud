package org.laganini.cloud.storage.audit.provider.jpa;

import app.jpa.JpaAuditTestConfiguration;
import org.laganini.cloud.storage.audit.provider.AbstractRevisionServiceTest;
import org.laganini.cloud.storage.audit.provider.jpa.converter.JpaRevisionConverter;
import org.laganini.cloud.storage.audit.provider.jpa.entity.JpaRevision;
import org.laganini.cloud.storage.audit.utils.AuditedUtils;
import org.laganini.cloud.test.suite.IntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@IntegrationTest
@ContextConfiguration(initializers = MariaDbAuditContextInitializer.class, classes = JpaAuditTestConfiguration.class)
public class JpaRevisionServiceTest extends AbstractRevisionServiceTest<JpaRevision> {

    @Autowired
    private JpaRevisionRepository revisionRepository;
    @Autowired
    private JpaRevisionService jpaRevisionService;

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
        return jpaRevisionService;
    }

}