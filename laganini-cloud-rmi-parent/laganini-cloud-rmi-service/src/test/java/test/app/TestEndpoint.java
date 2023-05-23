package test.app;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@FeignClient(value = Endpoint.APP, path = TestEndpoint.PATH)
public interface TestEndpoint {

    String PATH = "test";

    @Validated
    @GetMapping("/all")
    List<TestBody> getAll();

    @Validated
    @GetMapping("/all/reactive")
    Flux<TestBody> getAllReactive();

    @Validated
    @GetMapping("/{id}")
    Optional<TestBody> getById(@PathVariable @NotNull Long id);

    @Validated
    @GetMapping("/{id}/reactive")
    Mono<TestBody> getByIdReactive(@PathVariable @NotNull Long id);

    @Validated
    @PostMapping
    TestBody create(@RequestBody @Valid @NotNull TestBody testBody);

    @Validated
    @PostMapping("/{id}")
    TestBody update(@PathVariable @NotNull Long id, @RequestBody @Valid @NotNull TestBody testBody);

}
