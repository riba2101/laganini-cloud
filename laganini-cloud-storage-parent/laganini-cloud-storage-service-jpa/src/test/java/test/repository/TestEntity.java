package test.repository;

import org.laganini.cloud.storage.jpa.entity.AbstractIdEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

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
