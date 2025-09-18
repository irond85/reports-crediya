package co.irond.crediya.model.dto;

import java.math.BigDecimal;

public record UpdateStatisticsRequestDto(String metricName, BigDecimal amountToAdd) {
}
