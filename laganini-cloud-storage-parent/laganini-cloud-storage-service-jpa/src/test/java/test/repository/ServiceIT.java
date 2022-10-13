package test.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.laganini.cloud.storage.connector.model.*;
import org.laganini.cloud.storage.jpa.service.*;
import org.laganini.cloud.storage.jpa.test.JpaTest;
import org.laganini.cloud.storage.test.FlywayExtension;
import org.laganini.cloud.storage.jpa.test.MariaDbContextInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@JpaTest(initializers = MariaDbContextInitializer.class)
@ExtendWith(FlywayExtension.class)
public class ServiceIT {

    private final TestService service;

    @Autowired
    public ServiceIT(TestRepository repository) {
        this.service = new TestService(repository);
    }

    @Test
    void shouldCreate() {
        LocalDateTime now    = LocalDateTime.now();
        TestEntity    entity = new TestEntity("name");

        StepVerifier
                .create(service.create(entity))
                .assertNext(actual -> {
                    Assertions.assertThat(actual.getId()).isNotNull();
                    Assertions.assertThat(actual.getName()).isEqualTo(entity.getName());
                    Assertions.assertThat(actual.getCreatedAt()).isAfter(now);
                    Assertions.assertThat(actual.getUpdatedAt()).isAfter(now);
                    Assertions.assertThat(actual.getCreatedAt()).isEqualTo(actual.getUpdatedAt());
                })
                .verifyComplete();
    }

    @Test
    void shouldUpdate() {
        TestEntity entity  = new TestEntity("name");
        TestEntity created = service.create(entity).block();
        created.setName("updated");

        StepVerifier
                .create(service.update(created))
                .assertNext(actual -> {
                    Assertions.assertThat(actual.getId()).isNotNull();
                    Assertions.assertThat(actual.getName()).isEqualTo(created.getName());
                    Assertions.assertThat(actual.getCreatedAt()).isEqualTo(created.getCreatedAt());
                    Assertions.assertThat(actual.getUpdatedAt()).isAfter(actual.getCreatedAt());
                })
                .verifyComplete();
    }

    @Test
    void shouldFind() {
        TestEntity entity  = new TestEntity("name");
        TestEntity created = service.create(entity).block();

        StepVerifier
                .create(service.find(created.getId()))
                .assertNext(actual -> {
                    Assertions.assertThat(actual.getId()).isEqualTo(created.getId());
                    Assertions.assertThat(actual.getName()).isEqualTo(created.getName());
                })
                .verifyComplete();
    }

    @Test
    void shouldFindAll() {
        TestEntity created1 = service.create(new TestEntity("name1")).block();
        TestEntity created2 = service.create(new TestEntity("name2")).block();

        StepVerifier
                .create(service.findAll(List.of(created1.getId(), created2.getId())))
                .assertNext(actual -> {
                    Assertions.assertThat(actual.getId()).isEqualTo(created1.getId());
                    Assertions.assertThat(actual.getName()).isEqualTo(created1.getName());
                })
                .assertNext(actual -> {
                    Assertions.assertThat(actual.getId()).isEqualTo(created2.getId());
                    Assertions.assertThat(actual.getName()).isEqualTo(created2.getName());
                })
                .verifyComplete();
    }

    @Nested
    class Filtering {

        @Test
        void shouldFilter() {
            TestEntity created1 = service.create(new TestEntity("name1")).block();
            TestEntity created2 = service.create(new TestEntity("name2")).block();
            service.create(new TestEntity("not")).block();

            Filter filter = new Filter(List.of(new FilterCriteria<>(
                    TestSearchCriteriaMapper.NAME,
                    Operator.STARTS_WITH,
                    "name"
            )));
            StepVerifier
                    .create(service.filter(filter))
                    .assertNext(actual -> {
                        Assertions.assertThat(actual.getCount()).isEqualTo(2);
                        Assertions.assertThat(actual.getPage()).isEqualTo(Pageable.DEFAULT_PAGE);
                        Assertions.assertThat(actual.getPageSize()).isEqualTo(Pageable.DEFAULT_PAGE_SIZE);
                        Assertions.assertThat(actual.getData()).usingRecursiveComparison().isEqualTo(List.of(
                                created1,
                                created2
                        ));
                    })
                    .verifyComplete();
        }

        @Test
        void shouldPaginate() {
            TestEntity created1 = service.create(new TestEntity("name1")).block();
            TestEntity created2 = service.create(new TestEntity("name2")).block();
            TestEntity created3 = service.create(new TestEntity("name3")).block();
            TestEntity created4 = service.create(new TestEntity("name4")).block();
            TestEntity created5 = service.create(new TestEntity("name5")).block();
            TestEntity created6 = service.create(new TestEntity("name6")).block();
            TestEntity created7 = service.create(new TestEntity("name7")).block();
            TestEntity created8 = service.create(new TestEntity("name8")).block();

            Filter filter = new Filter(
                    List.of(new FilterCriteria<>(TestSearchCriteriaMapper.NAME, Operator.STARTS_WITH, "name")),
                    Collections.emptyList(),
                    new Pageable(2, 3)
            );
            StepVerifier
                    .create(service.filter(filter))
                    .assertNext(actual -> {
                        Assertions.assertThat(actual.getCount()).isEqualTo(8);
                        Assertions.assertThat(actual.getPage()).isEqualTo(2);
                        Assertions.assertThat(actual.getPageSize()).isEqualTo(3);
                        Assertions.assertThat(actual.getData()).usingRecursiveComparison().isEqualTo(List.of(
                                created4,
                                created5,
                                created6
                        ));
                    })
                    .verifyComplete();
        }

        @Test
        void shouldSort() {
            TestEntity created1 = service.create(new TestEntity("name1")).block();
            TestEntity created2 = service.create(new TestEntity("name2")).block();
            TestEntity created3 = service.create(new TestEntity("name3")).block();
            TestEntity created4 = service.create(new TestEntity("name4")).block();
            TestEntity created5 = service.create(new TestEntity("name5")).block();
            TestEntity created6 = service.create(new TestEntity("name6")).block();
            TestEntity created7 = service.create(new TestEntity("name7")).block();
            TestEntity created8 = service.create(new TestEntity("name8")).block();

            Filter filter = new Filter(
                    Collections.emptyList(),
                    List.of(new SortCriteria(TestSearchCriteriaMapper.NAME, Direction.DESC))
            );
            StepVerifier
                    .create(service.filter(filter))
                    .assertNext(actual -> {
                        Assertions.assertThat(actual.getCount()).isEqualTo(8);
                        Assertions.assertThat(actual.getPage()).isEqualTo(Pageable.DEFAULT_PAGE);
                        Assertions.assertThat(actual.getPageSize()).isEqualTo(Pageable.DEFAULT_PAGE_SIZE);
                        Assertions.assertThat(actual.getData()).usingRecursiveComparison().isEqualTo(List.of(
                                created8,
                                created7,
                                created6,
                                created5,
                                created4,
                                created3,
                                created2,
                                created1
                        ));
                    })
                    .verifyComplete();
        }

    }

    private static class TestService
            implements JpaCreatableService<Long, TestEntity, TestRepository>,
                       JpaDeletableService<Long, TestEntity, TestRepository>,
                       JpaFilterableService<Long, TestEntity, TestRepository>,
                       JpaFindableService<Long, TestEntity, TestRepository>,
                       JpaPeristableService<Long, TestEntity, TestRepository>,
                       JpaUpdateableService<Long, TestEntity, TestRepository>
    {

        private final TestRepository repository;

        private TestService(TestRepository repository) {
            this.repository = repository;
        }

        @Override
        public TestRepository getRepository() {
            return repository;
        }

    }

}
