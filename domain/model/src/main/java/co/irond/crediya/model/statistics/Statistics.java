package co.irond.crediya.model.statistics;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
public class Statistics {
    private String metricName;
    private Long recordCount;
    private BigDecimal totalAmount;
}
