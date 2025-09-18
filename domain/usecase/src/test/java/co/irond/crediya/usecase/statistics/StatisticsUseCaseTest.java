package co.irond.crediya.usecase.statistics;

import co.irond.crediya.model.dto.StatisticsRequestDto;
import co.irond.crediya.model.exceptions.CrediYaException;
import co.irond.crediya.model.exceptions.ErrorCode;
import co.irond.crediya.model.logs.gateway.LoggerGateway;
import co.irond.crediya.model.statistics.Statistics;
import co.irond.crediya.model.statistics.gateways.StatisticsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatisticsUseCaseTest {

    @Mock
    private StatisticsRepository statisticsRepository;
    @Mock
    private LoggerGateway logger;
    @InjectMocks
    private StatisticsUseCase statisticsUseCase;

    private Statistics existingStatistics;
    private StatisticsRequestDto requestDto;

    @BeforeEach
    void setUp() {
        existingStatistics = Statistics.builder()
                .metricName("loanApplicationsApproved")
                .recordCount(10L)
                .totalAmount(BigDecimal.valueOf(50000.0))
                .build();
        requestDto = new StatisticsRequestDto("loanApplicationsApproved", BigDecimal.valueOf(100.0));
    }

    @Test
    void save_shouldIncrementExistingStatistics() {
        when(statisticsRepository.findById(anyString()))
                .thenReturn(Mono.just(existingStatistics));
        when(statisticsRepository.save(any(Statistics.class)))
                .thenReturn(Mono.just(existingStatistics.toBuilder()
                        .recordCount(11L)
                        .totalAmount(BigDecimal.valueOf(50100.0))
                        .build()));

        Mono<Statistics> result = statisticsUseCase.save(requestDto);

        StepVerifier.create(result)
                .expectNextMatches(stats ->
                        "loanApplicationsApproved".equals(stats.getMetricName()) &&
                                stats.getRecordCount().equals(11L) &&
                                stats.getTotalAmount().equals(BigDecimal.valueOf(50100.0))
                )
                .verifyComplete();

        verify(statisticsRepository, times(1)).findById(anyString());
        verify(statisticsRepository, times(2)).save(any(Statistics.class));
        verify(logger, times(1)).info(anyString(), anyString());
        verify(logger, times(2)).info(anyString(), any(Statistics.class));
    }

    @Test
    void save_shouldCreateNewStatisticsIfNotFound() {
        // Given no existing record
        when(statisticsRepository.findById(anyString())).thenReturn(Mono.empty());
        when(statisticsRepository.save(any(Statistics.class))).thenReturn(Mono.just(Statistics.builder()
                .metricName("loanApplicationsApproved")
                .recordCount(1L)
                .totalAmount(BigDecimal.valueOf(100.0))
                .build()));

        Mono<Statistics> result = statisticsUseCase.save(requestDto);

        StepVerifier.create(result)
                .expectNextMatches(stats ->
                        "loanApplicationsApproved".equals(stats.getMetricName()) &&
                                stats.getRecordCount().equals(1L) &&
                                stats.getTotalAmount().equals(BigDecimal.valueOf(100.0))
                )
                .verifyComplete();

        verify(statisticsRepository, times(1)).findById(anyString());
        verify(statisticsRepository, times(2)).save(any(Statistics.class)); // save is called twice: once in switchIfEmpty, once in flatMap
        verify(logger, times(1)).info(anyString(), anyString());
        verify(logger, times(1)).info(anyString(), any(Statistics.class));
    }

    @Test
    void save_shouldReturnErrorForNegativeAmount() {
        StatisticsRequestDto invalidDto = new StatisticsRequestDto("invalidMetric", BigDecimal.valueOf(-100.0));

        Mono<Statistics> result = statisticsUseCase.save(invalidDto);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof CrediYaException &&
                                ((CrediYaException) throwable).getErrorCode().equals(ErrorCode.INVALID_AMOUNT)
                )
                .verify();

        verify(statisticsRepository, never()).findById(anyString());
        verify(statisticsRepository, never()).save(any(Statistics.class));
        verify(logger, times(1)).info(anyString(), anyString());
    }

    @Test
    void findById_shouldReturnStatisticsIfFound() {
        when(statisticsRepository.findById(anyString())).thenReturn(Mono.just(existingStatistics));

        Mono<Statistics> result = statisticsUseCase.findById("loanApplicationsApproved");

        StepVerifier.create(result)
                .expectNext(existingStatistics)
                .verifyComplete();

        verify(statisticsRepository, times(1)).findById(anyString());
        verify(logger, times(1)).info(anyString(), any(Statistics.class));
    }

    @Test
    void findById_shouldReturnEmptyMonoIfNotFound() {
        when(statisticsRepository.findById(anyString())).thenReturn(Mono.empty());

        Mono<Statistics> result = statisticsUseCase.findById("nonExistentMetric");

        StepVerifier.create(result)
                .expectComplete();

        verify(statisticsRepository, times(1)).findById(anyString());
        verify(logger, never()).info(anyString(), any(Statistics.class));
    }
}
