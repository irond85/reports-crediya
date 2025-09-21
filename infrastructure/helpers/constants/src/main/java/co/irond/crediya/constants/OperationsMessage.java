package co.irond.crediya.constants;

import lombok.Getter;

@Getter
public enum OperationsMessage {
    REQUEST_RECEIVED("Statistics received from db {}"),
    OPERATION_ERROR("Error failed service transactional: {}"),
    SAVE_OK("Statistics saved successfully.");


    private final String message;

    OperationsMessage(String message) {
        this.message = message;
    }
}