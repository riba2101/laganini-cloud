package test.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.laganini.cloud.data.jpa.entity.AbstractIdEntity;
import org.laganini.cloud.data.jpa.entity.JpaPurgeableReference;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "purgeable")
public class PurgeableEntity
        extends AbstractIdEntity<Long>
        implements org.laganini.cloud.data.entity.PurgeableEntity<JpaPurgeableReference>
{

    private String                name;
    private JpaPurgeableReference purgeable;

}
