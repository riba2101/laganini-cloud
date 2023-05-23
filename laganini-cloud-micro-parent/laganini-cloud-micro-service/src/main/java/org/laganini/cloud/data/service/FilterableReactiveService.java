package org.laganini.cloud.data.service;

import org.laganini.cloud.data.connector.model.Filter;
import org.laganini.cloud.data.connector.model.FilteredAndSorted;
import reactor.core.publisher.Mono;

public interface FilterableReactiveService<ID, ENTITY> extends FindableService<ID, ENTITY> {

    Mono<FilteredAndSorted<ENTITY>> filter(Filter filter);

}
