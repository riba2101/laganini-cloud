package org.laganini.cloud.observability.tracing.author;

import lombok.extern.slf4j.Slf4j;
import org.laganini.cloud.observability.SpanConstants;
import reactor.core.publisher.Mono;

@Slf4j
public class DefaultAuthorReactiveProvider
        implements AuthorReactiveProvider
{

    private final AuthorProvider authorProvider;

    public DefaultAuthorReactiveProvider(AuthorProvider authorProvider) {
        this.authorProvider = authorProvider;
    }

    @Override
    public Mono<String> provide() {
        try {
            return Mono.justOrEmpty(authorProvider.provide());
        } catch (Exception e) {
            log.debug("Exception trying to get propagated field: {}", SpanConstants.ACCOUNT_ID_KEY, e);
        }

        return Mono.empty();
    }

}
