package org.laganini.cloud.observability.tracing.author;

import brave.baggage.BaggageField;
import org.laganini.cloud.observability.SpanConstants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultAuthorProvider implements AuthorProvider {

    @Override
    public String provide() {
        try {
            return BaggageField.getByName(SpanConstants.ACCOUNT_ID_KEY).getValue();
        } catch (Exception e) {
            log.debug("Exception trying to get propagated field: {}", SpanConstants.ACCOUNT_ID_KEY, e);
        }

        return null;
    }

}
