package co.irond.crediya.model.dto;

import java.math.BigDecimal;

public record StatisticsRequestDto(String metricName, BigDecimal amountToAdd) {
}
