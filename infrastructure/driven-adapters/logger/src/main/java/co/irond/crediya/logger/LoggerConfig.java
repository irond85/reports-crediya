package co.irond.crediya.logger;

import co.irond.crediya.model.logs.gateway.LoggerGateway;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class LoggerConfig {

    @Bean
    @Scope("prototype")
    public LoggerGateway logger(InjectionPoint injectionPoint) {
        return new Slf4jAdapter(injectionPoint.getMember().getDeclaringClass());
    }
}