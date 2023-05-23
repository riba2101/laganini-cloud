package test.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.laganini.cloud.data.jpa.entity.AbstractIdEntity;
import org.laganini.cloud.data.jpa.entity.JpaOwnerReference;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "owned")
public class OwnedEntity
        extends AbstractIdEntity<Long>
        implements org.laganini.cloud.data.entity.OwnedEntity<Long, JpaOwnerReference<Long>>
{

    private String                  name;
    @Embedded
    @Column(name = "owner_id")
    private JpaOwnerReference<Long> owner;

}
