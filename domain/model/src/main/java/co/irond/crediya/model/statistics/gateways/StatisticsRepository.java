package co.irond.crediya.model.statistics.gateways;

import co.irond.crediya.model.statistics.Statistics;
import reactor.core.publisher.Mono;

public interface StatisticsRepository {
    Mono<Statistics> save(Statistics statistics);

    Mono<Statistics> findById(String id);
}
