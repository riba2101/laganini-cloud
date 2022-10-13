package app.jpa;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.laganini.cloud.storage.audit.annotation.Audited;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.jpa.entity.AbstractIdEntity;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Audited(name = "jpa")
@Table(name = "dummy")
@Entity
public class Dummy extends AbstractIdEntity<Integer> implements IdentityEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String  name;

    public Dummy(String name) {
        this.name = name;
    }

}
