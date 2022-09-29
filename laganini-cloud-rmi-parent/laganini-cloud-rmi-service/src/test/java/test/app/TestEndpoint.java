package test.app;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactivefeign.spring.config.ReactiveFeignClient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@ReactiveFeignClient(value = Endpoint.APP, path = TestEndpoint.PATH)
public interface TestEndpoint {

    String PATH = "test";

    @Validated
    @GetMapping
    List<TestBody> getAll();

    @Validated
    @GetMapping("/{id}")
    Optional<TestBody> getById(@PathVariable @NotNull Long id);

    @Validated
    @PostMapping
    TestBody create(@RequestBody @Valid @NotNull TestBody testBody);

    @Validated
    @PostMapping("/{id}")
    TestBody update(@PathVariable @NotNull Long id, @RequestBody @Valid @NotNull TestBody testBody);

}
