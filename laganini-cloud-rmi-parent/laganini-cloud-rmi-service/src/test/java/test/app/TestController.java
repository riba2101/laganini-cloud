package test.app;

import io.micrometer.core.annotation.Timed;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Timed
@RestController
@RequestMapping(path = TestController.PATH)
record TestController(TestRepository testRepository) implements TestEndpoint {

    @Validated
    @Override
    public List<TestBody> getAll() {
        return testRepository
                .findAll()
                .stream()
                .map(this::from)
                .toList();
    }

    @Validated
    @Override
    public Optional<TestBody> getById(@PathVariable @NotNull Long id) {
        Optional<TestEntity> testEntity = testRepository.findById(id);
        return testEntity.map(this::from);
    }

    @Validated
    @Override
    public TestBody create(@RequestBody @Valid @NotNull TestBody testBody) {
        testBody.setName(null);
        return from(testRepository.save(to(testBody)));
    }

    @Validated
    @Override
    public TestBody update(@PathVariable @NotNull Long id, @RequestBody @Valid @NotNull TestBody testBody) {
        return from(testRepository.save(to(testBody)));
    }

    private TestBody from(TestEntity entity) {
        return new TestBody(entity.getId(), entity.getName());
    }

    private TestEntity to(TestBody body) {
        return new TestEntity(body.getId(), body.getName());
    }

}
