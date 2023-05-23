package test.repository;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.laganini.cloud.data.r2dbc.entity.AbstractIdEntity;
import org.laganini.cloud.data.r2dbc.entity.R2dbcPurgeableReference;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "purgeable")
public class PurgeableEntity
        extends AbstractIdEntity<Long>
        implements org.laganini.cloud.data.entity.PurgeableEntity<R2dbcPurgeableReference>
{

    private String                  name;
    private R2dbcPurgeableReference purgeable;

    public PurgeableEntity(Long id, String name, R2dbcPurgeableReference purgeable) {
        super(id);

        this.name = name;
        this.purgeable = purgeable;
    }

//    @Override
//    public R2dbcPurgeableReference getPurgeable() {
//        return purgeable;
//    }
//
//    @Override
//    public void setPurgeable(R2dbcPurgeableReference purgeable) {
//        this.purgeable = purgeable;
//    }

}
