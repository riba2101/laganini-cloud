package test.repository;

import org.laganini.cloud.storage.filter.AbstractSearchCriteriaMapper;

public class TestSearchCriteriaMapper extends AbstractSearchCriteriaMapper {

    public static final String NAME = "name";

    public TestSearchCriteriaMapper() {
        registerField(NAME, QTestEntity.testEntity.name);
    }

}
