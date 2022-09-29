package test.app;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Entity
@Table(name = "test")
public class TestEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @NotNull
    public  Long   id;
    @NotBlank
    private String name;

}
