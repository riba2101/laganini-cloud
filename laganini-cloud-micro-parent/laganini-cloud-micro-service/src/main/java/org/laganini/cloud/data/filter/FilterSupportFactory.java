package org.laganini.cloud.data.filter;

import org.laganini.cloud.data.support.DateTimeService;

public class FilterSupportFactory {

    private final DateTimeService dateTimeService;

    public FilterSupportFactory(DateTimeService dateTimeService) {
        this.dateTimeService = dateTimeService;
    }

    public org.laganini.cloud.data.filter.FilterSupport build(AbstractSearchCriteriaMapper mapper) {
        return new FilterSupport(mapper, dateTimeService);
    }

}
