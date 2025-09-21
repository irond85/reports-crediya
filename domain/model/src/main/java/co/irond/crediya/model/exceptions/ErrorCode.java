package co.irond.crediya.model.exceptions;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INVALID_AMOUNT("BA_NR_001", "The amount is not valid.", 422),
    INVALID_TOKEN("BS_T_403_01", "Invalid authentication in the request.", 403);

    private final String internCode;
    private final String message;
    private final int httpCode;

    ErrorCode(String internCode, String message, int httpCode) {
        this.internCode = internCode;
        this.message = message;
        this.httpCode = httpCode;
    }

}
