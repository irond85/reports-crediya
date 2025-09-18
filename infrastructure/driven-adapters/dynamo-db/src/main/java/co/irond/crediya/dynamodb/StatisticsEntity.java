package co.irond.crediya.dynamodb;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.math.BigDecimal;

@DynamoDbBean
public class StatisticsEntity {

    private String metricName;
    private Long recordCount;
    private BigDecimal totalAmount;

    public StatisticsEntity() {
    }

    public StatisticsEntity(String metricName, Long recordCount, BigDecimal totalAmount) {
        this.metricName = metricName;
        this.recordCount = recordCount;
        this.totalAmount = totalAmount;
    }

    @DynamoDbPartitionKey
    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public Long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Long recordCount) {
        this.recordCount = recordCount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
