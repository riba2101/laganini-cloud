package org.laganini.cloud.logging.author;

import brave.baggage.BaggageField;
import org.laganini.cloud.common.SpanConstants;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class DefaultAuthorReactiveProvider
        implements AuthorReactiveProvider {

    @Override
    public Mono<String> provide() {
        try {
            return Mono.just(BaggageField.getByName(SpanConstants.ACCOUNT_ID_KEY).getValue());
        } catch (Exception e) {
            log.debug("Exception trying to get propagated field: {}", SpanConstants.ACCOUNT_ID_KEY, e);
        }

        return Mono.empty();
    }

}
