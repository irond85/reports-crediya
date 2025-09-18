package co.irond.crediya.sqs.listener;

import co.irond.crediya.model.dto.StatisticsRequestDto;
import co.irond.crediya.model.exceptions.CrediYaException;
import co.irond.crediya.sqs.listener.dto.SQSMessageDto;
import co.irond.crediya.usecase.statistics.StatisticsUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class SQSProcessor implements Function<Message, Mono<Void>> {
    private final StatisticsUseCase statisticsUseCase;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> apply(Message message) {
        return Mono.fromCallable(() -> objectMapper.readValue(message.body(), SQSMessageDto.class))
                .flatMap(sqsMessageDto -> {
                    StatisticsRequestDto statisticsRequestDto = new StatisticsRequestDto(sqsMessageDto.metricName(), sqsMessageDto.amountToAdd());

                    return statisticsUseCase.save(statisticsRequestDto)
                            .onErrorResume(CrediYaException.class, e ->
                                    Mono.empty()
                            );
                })
                .then();
    }
}
