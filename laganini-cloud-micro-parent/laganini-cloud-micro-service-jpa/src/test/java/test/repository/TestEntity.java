package test.repository;

import lombok.*;
import org.laganini.cloud.storage.jpa.entity.AbstractIdEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Entity
@Table(name = "test")
public class TestEntity
        extends AbstractIdEntity<Long>
{

    private String name;

}
