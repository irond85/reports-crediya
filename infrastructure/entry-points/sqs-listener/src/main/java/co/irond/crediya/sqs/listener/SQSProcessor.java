package co.irond.crediya.sqs.listener;

import co.irond.crediya.model.dto.StatisticsRequestDto;
import co.irond.crediya.sqs.listener.dto.SQSMessageDto;
import co.irond.crediya.usecase.statistics.StatisticsUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class SQSProcessor implements Function<Message, Mono<Void>> {
    private final StatisticsUseCase statisticsUseCase;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> apply(Message message) {
        return Mono.fromCallable(() -> objectMapper.readValue(message.body(), SQSMessageDto.class))
                .doOnError(ex -> log.error("Error al recibir SQS ", ex))
                .flatMap(sqsMessageDto -> {
                    StatisticsRequestDto statisticsRequestDto = new StatisticsRequestDto(sqsMessageDto.metricName(), sqsMessageDto.amountToAdd());

                    return statisticsUseCase.save(statisticsRequestDto).then();
                })
                .then();
    }
}
