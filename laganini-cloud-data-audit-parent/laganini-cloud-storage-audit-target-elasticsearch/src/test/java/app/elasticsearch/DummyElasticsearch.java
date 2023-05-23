package app.elasticsearch;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.laganini.cloud.data.audit.annotation.Audited;
import org.laganini.cloud.data.audit.target.TargetType;
import org.laganini.cloud.data.entity.IdentityEntity;
import org.laganini.cloud.data.jpa.entity.AbstractIdEntity;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Audited(name = "dummy", target = TargetType.ELASTICSEARCH)
@Document(indexName = "dummy")
@Entity
public class DummyElasticsearch extends AbstractIdEntity<Integer> implements IdentityEntity<Integer> {

    private String name;

    public DummyElasticsearch(String name) {
        this.name = name;
    }

}
