package co.irond.crediya.api;

import co.irond.crediya.api.dto.ApiResponseDto;
import co.irond.crediya.constants.OperationsMessage;
import co.irond.crediya.usecase.statistics.StatisticsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

    private final StatisticsUseCase statisticsUseCase;

    @Operation(
            operationId = "getAllApplicationsPaging",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "get applications approved report successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = ApiResponseDto.class)
                            )
                    )
            }
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    public Mono<ServerResponse> listenGETStatisticsUseCase(ServerRequest serverRequest) {
        return statisticsUseCase.findById("loanApplicationsApproved")
                .flatMap(statistics -> {
                    ApiResponseDto<Object> response = ApiResponseDto.builder()
                            .status("Success")
                            .message(OperationsMessage.REQUEST_RECEIVED.getMessage())
                            .data(statistics).build();
                    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(response);
                });
    }
}
