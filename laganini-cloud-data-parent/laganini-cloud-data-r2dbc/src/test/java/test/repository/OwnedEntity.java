package test.repository;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.laganini.cloud.data.r2dbc.entity.AbstractIdEntity;
import org.laganini.cloud.data.r2dbc.entity.R2dbcOwnerReference;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "owned")
public class OwnedEntity
        extends AbstractIdEntity<Long>
        implements org.laganini.cloud.data.entity.OwnedEntity<Long, R2dbcOwnerReference<Long>>
{

    private String                    name;
    private R2dbcOwnerReference<Long> owner;

    public OwnedEntity(Long id, String name, R2dbcOwnerReference<Long> owner) {
        super(id);

        this.name = name;
        this.owner = owner;
    }

//    @Override
//    public R2dbcOwnerReference<Long> getOwner() {
//        return owner;
//    }
//
//    @Override
//    public void setOwner(R2dbcOwnerReference<Long> owner) {
//        this.owner = owner;
//    }

}
