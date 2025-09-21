package co.irond.crediya.logger;

import co.irond.crediya.model.logs.gateway.LoggerGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jAdapter implements LoggerGateway {

    private final Logger logger;

    public Slf4jAdapter(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    @Override
    public void debug(String message, Object... args) {
        logger.debug(message, args);
    }

    @Override
    public void info(String message, Object... args) {
        logger.info(message, args);
    }

    @Override
    public void warn(String message, Object... args) {
        logger.warn(message, args);
    }

    @Override
    public void error(String message, Object... args) {
        logger.error(message, args);
    }
}