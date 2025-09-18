package co.irond.crediya.usecase.statistics;

import co.irond.crediya.model.dto.UpdateStatisticsRequestDto;
import co.irond.crediya.model.exceptions.CrediYaException;
import co.irond.crediya.model.exceptions.ErrorCode;
import co.irond.crediya.model.logs.gateway.LoggerGateway;
import co.irond.crediya.model.statistics.Statistics;
import co.irond.crediya.model.statistics.gateways.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Objects;

@RequiredArgsConstructor
public class StatisticsUseCase {

    private final StatisticsRepository statisticsRepository;
    private final LoggerGateway logger;

    public Mono<Statistics> save(UpdateStatisticsRequestDto updateStatisticsRequestDto) {
        logger.info("Saving data {}", updateStatisticsRequestDto.toString());
        if (updateStatisticsRequestDto.amountToAdd().compareTo(BigDecimal.ZERO) < 0) {
            return Mono.error(new CrediYaException(ErrorCode.INVALID_AMOUNT));
        }

        return findById(updateStatisticsRequestDto.metricName())
                .filter(Objects::nonNull)
                .switchIfEmpty(statisticsRepository.save(Statistics.builder().metricName("loanApplicationsApproved").recordCount(0L).totalAmount(BigDecimal.ZERO).build()))
                .map(statistics -> statistics.toBuilder().recordCount(statistics.getRecordCount() + 1L).totalAmount(statistics.getTotalAmount().add(updateStatisticsRequestDto.amountToAdd())).build())
                .flatMap(statisticsRepository::save)
                .doOnError(ex -> logger.error("Error saving statistics request", ex))
                .doOnSuccess(statistics -> logger.info("Statistics request saved {}", statistics.toString()));
    }

    public Mono<Statistics> findById(String id) {
        return statisticsRepository.findById(id)
                .doOnError(ex -> logger.error("Error getting statistics", ex))
                .doOnSuccess(statistics -> logger.info("statistics retrieved {}", statistics.toString()));
    }
}
