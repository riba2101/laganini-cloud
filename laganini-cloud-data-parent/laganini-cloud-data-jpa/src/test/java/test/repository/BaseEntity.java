package test.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.laganini.cloud.data.jpa.entity.AbstractIdEntity;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Entity
@Table(name = "base")
public class BaseEntity
        extends AbstractIdEntity<Long>
{

    private String name;

}
