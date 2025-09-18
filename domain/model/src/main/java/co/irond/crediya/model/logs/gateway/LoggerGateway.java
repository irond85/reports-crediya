package co.irond.crediya.model.logs.gateway;

public interface LoggerGateway {

    void debug(String message, Object... args);

    void info(String message, Object... args);

    void warn(String message, Object... args);

    void error(String message, Object... args);
}
