package test.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.laganini.cloud.data.jpa.entity.AbstractIdEntity;
import org.laganini.cloud.data.jpa.entity.JpaTemporalReference;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "temporal")
public class TemporalEntity
        extends AbstractIdEntity<Long>
        implements org.laganini.cloud.data.entity.TemporalEntity<JpaTemporalReference>
{

    private String               name;
    private JpaTemporalReference temporal;

}
