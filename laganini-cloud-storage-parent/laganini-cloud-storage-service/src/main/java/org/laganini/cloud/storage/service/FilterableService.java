package org.laganini.cloud.storage.service;

import org.laganini.cloud.storage.connector.model.Filter;
import org.laganini.cloud.storage.connector.model.FilteredAndSorted;
import reactor.core.publisher.Mono;

public interface FilterableService<ID, ENTITY> extends FindableService<ID, ENTITY> {

    Mono<FilteredAndSorted<ENTITY>> filter(Filter filter);

}
