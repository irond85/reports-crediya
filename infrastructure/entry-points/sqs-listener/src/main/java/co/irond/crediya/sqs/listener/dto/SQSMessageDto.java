package co.irond.crediya.sqs.listener.dto;

import java.math.BigDecimal;

public record SQSMessageDto(String metricName, BigDecimal amountToAdd) {
}
