package app.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DummyElasticsearchRepository extends ElasticsearchRepository<DummyElasticsearch, Integer> {

}
