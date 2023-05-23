package test.app;

import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class TestEntity {

    @NotNull
    public  Long   id;
    @NotBlank
    private String name;

}
