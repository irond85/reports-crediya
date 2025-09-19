package co.irond.crediya.api;

import co.irond.crediya.api.dto.ReportsPath;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RouterRest {

    private final ReportsPath reportsPath;

    @Bean
    @RouterOperations({
            @RouterOperation(path = "/api/v1/reportes", method = RequestMethod.GET, beanClass = Handler.class, beanMethod = "listenGETStatisticsUseCase")
    })
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(GET(reportsPath.getV1() + reportsPath.getReportes()), handler::listenGETStatisticsUseCase);
    }
}
