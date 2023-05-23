package app.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.laganini.cloud.data.audit.annotation.Audited;
import org.laganini.cloud.data.audit.target.TargetType;
import org.laganini.cloud.data.entity.IdentityEntity;
import org.laganini.cloud.data.jpa.entity.AbstractIdEntity;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Audited(name = "dummy", target = TargetType.ELASTICSEARCH)
@Table(name = "dummy")
@Entity
public class DummyJpa extends AbstractIdEntity<Integer> implements IdentityEntity<Integer> {

    private String name;

    public DummyJpa(String name) {
        this.name = name;
    }

}
