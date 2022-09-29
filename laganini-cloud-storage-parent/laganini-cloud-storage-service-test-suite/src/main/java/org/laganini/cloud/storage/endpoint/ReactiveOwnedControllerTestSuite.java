package org.laganini.cloud.storage.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.laganini.cloud.storage.connector.model.AbstractOwnedBase;
import org.laganini.cloud.storage.connector.model.AbstractOwnedContext;
import org.laganini.cloud.storage.entity.OwnedEntity;
import org.springframework.context.ApplicationContext;

public abstract class ReactiveOwnedControllerTestSuite<
        ID,
        MODEL extends OwnedEntity<ID>,
        CONTEXT extends AbstractOwnedContext<ID>,
        RESPONSE extends AbstractOwnedBase<ID>
        >
        extends ReactiveCRUDControllerTestSuite<ID, MODEL, CONTEXT, RESPONSE>
{

    protected ReactiveOwnedControllerTestSuite(
            ApplicationContext context,
            ObjectMapper objectMapper,
            String basePath
    )
    {
        super(context, objectMapper, basePath);
    }

}