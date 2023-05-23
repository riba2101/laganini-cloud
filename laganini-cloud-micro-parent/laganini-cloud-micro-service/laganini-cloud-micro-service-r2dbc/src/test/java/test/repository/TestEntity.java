package test.repository;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Getter;
import lombok.Setter;
import org.laganini.cloud.storage.r2dbc.entity.AbstractIdEntity;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@QueryEntity
@Table("test")
public class TestEntity
        extends AbstractIdEntity<Long>
{

    private String name;

    public TestEntity(Long id, String name) {
        super(id);

        this.name = name;
    }

}
