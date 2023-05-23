package test.repository;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.laganini.cloud.data.r2dbc.entity.AbstractIdEntity;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "base")
public class BaseEntity
        extends AbstractIdEntity<Long>
{

    private String name;

    public BaseEntity(Long id, String name) {
        super(id);

        this.name = name;
    }

}
