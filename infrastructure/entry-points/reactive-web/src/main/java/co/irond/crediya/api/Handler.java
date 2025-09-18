package co.irond.crediya.api;

import co.irond.crediya.model.dto.UpdateStatisticsRequestDto;
import co.irond.crediya.model.statistics.Statistics;
import co.irond.crediya.usecase.statistics.StatisticsUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class Handler {

    private final StatisticsUseCase statisticsUseCase;

    public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {
        log.info("recibe get");
        return ok().contentType(MediaType.APPLICATION_JSON).body(statisticsUseCase.findById("loanApplicationsApproved"), Statistics.class);
    }

    public Mono<ServerResponse> listenPOSTUseCase(ServerRequest serverRequest) {
        log.info("recibe POST");
        UpdateStatisticsRequestDto updateRequest = new UpdateStatisticsRequestDto("loanApplicationsApproved", BigDecimal.TEN);
        return ok().contentType(MediaType.APPLICATION_JSON).body(statisticsUseCase.save(updateRequest), Statistics.class);
    }
}
