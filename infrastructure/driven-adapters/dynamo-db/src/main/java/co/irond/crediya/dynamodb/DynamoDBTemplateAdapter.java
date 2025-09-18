package co.irond.crediya.dynamodb;

import co.irond.crediya.dynamodb.helper.TemplateAdapterOperations;
import co.irond.crediya.model.statistics.Statistics;
import co.irond.crediya.model.statistics.gateways.StatisticsRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;


@Repository
public class DynamoDBTemplateAdapter extends TemplateAdapterOperations<Statistics, String, StatisticsEntity> implements StatisticsRepository {

    public DynamoDBTemplateAdapter(DynamoDbEnhancedAsyncClient connectionFactory, ObjectMapper mapper) {
        super(connectionFactory, mapper, d -> mapper.map(d, Statistics.class), "statistics");
    }

    @Override
    public Mono<Statistics> findById(String id) {
        return getById(id);
    }
}
