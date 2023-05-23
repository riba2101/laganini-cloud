package test.repository;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.laganini.cloud.data.r2dbc.entity.AbstractIdEntity;
import org.laganini.cloud.data.r2dbc.entity.R2dbcTemporalReference;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "temporal")
public class TemporalEntity
        extends AbstractIdEntity<Long>
        implements org.laganini.cloud.data.entity.TemporalEntity<R2dbcTemporalReference>
{

    private String                 name;
    private R2dbcTemporalReference temporal;

    public TemporalEntity(Long id, String name, R2dbcTemporalReference temporal) {
        super(id);

        this.name = name;
        this.temporal = temporal;
    }

//    @Override
//    public R2dbcTemporalReference getTemporal() {
//        return temporal;
//    }
//
//    @Override
//    public void setTemporal(R2dbcTemporalReference temporal) {
//        this.temporal = temporal;
//    }

}
