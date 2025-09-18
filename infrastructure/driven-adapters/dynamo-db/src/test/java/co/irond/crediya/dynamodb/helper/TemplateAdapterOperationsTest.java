//package co.irond.crediya.dynamodb.helper;
//
//import co.irond.crediya.dynamodb.DynamoDBTemplateAdapter;
//import co.irond.crediya.dynamodb.StatisticsEntity;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.reactivecommons.utils.ObjectMapper;
//import reactor.test.StepVerifier;
//import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
//import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
//import software.amazon.awssdk.enhanced.dynamodb.Key;
//import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
//
//import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
//import java.util.concurrent.CompletableFuture;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.when;
//
//class TemplateAdapterOperationsTest {
//
//    @Mock
//    private DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient;
//
//    @Mock
//    private ObjectMapper mapper;
//
//    @Mock
//    private DynamoDbAsyncTable<StatisticsEntity> customerTable;
//
//    private StatisticsEntity statisticsEntity;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        when(dynamoDbEnhancedAsyncClient.table("table_name", TableSchema.fromBean(StatisticsEntity.class)))
//                .thenReturn(customerTable);
//
//        statisticsEntity = new StatisticsEntity();
//        statisticsEntity.setId("id");
//        statisticsEntity.setAtr1("atr1");
//    }
//
//    @Test
//    void modelEntityPropertiesMustNotBeNull() {
//        StatisticsEntity statisticsEntityUnderTest = new StatisticsEntity("id", "atr1");
//
//        assertNotNull(statisticsEntityUnderTest.getId());
//        assertNotNull(statisticsEntityUnderTest.getAtr1());
//    }
//
//    @Test
//    void testSave() {
//        when(customerTable.putItem(statisticsEntity)).thenReturn(CompletableFuture.runAsync(()->{}));
//        when(mapper.map(statisticsEntity, StatisticsEntity.class)).thenReturn(statisticsEntity);
//
//        DynamoDBTemplateAdapter dynamoDBTemplateAdapter =
//                new DynamoDBTemplateAdapter(dynamoDbEnhancedAsyncClient, mapper);
//
//        StepVerifier.create(dynamoDBTemplateAdapter.save(statisticsEntity))
//                .expectNextCount(1)
//                .verifyComplete();
//    }
//
//    @Test
//    void testGetById() {
//        String id = "id";
//
//        when(customerTable.getItem(
//                Key.builder().partitionValue(AttributeValue.builder().s(id).build()).build()))
//                .thenReturn(CompletableFuture.completedFuture(statisticsEntity));
//        when(mapper.map(statisticsEntity, Object.class)).thenReturn("value");
//
//        DynamoDBTemplateAdapter dynamoDBTemplateAdapter =
//                new DynamoDBTemplateAdapter(dynamoDbEnhancedAsyncClient, mapper);
//
//        StepVerifier.create(dynamoDBTemplateAdapter.getById("id"))
//                .expectNext("value")
//                .verifyComplete();
//    }
//
//    @Test
//    void testDelete() {
//        when(mapper.map(statisticsEntity, StatisticsEntity.class)).thenReturn(statisticsEntity);
//        when(mapper.map(statisticsEntity, Object.class)).thenReturn("value");
//
//        when(customerTable.deleteItem(statisticsEntity))
//                .thenReturn(CompletableFuture.completedFuture(statisticsEntity));
//
//        DynamoDBTemplateAdapter dynamoDBTemplateAdapter =
//                new DynamoDBTemplateAdapter(dynamoDbEnhancedAsyncClient, mapper);
//
//        StepVerifier.create(dynamoDBTemplateAdapter.delete(statisticsEntity))
//                .expectNext("value")
//                .verifyComplete();
//    }
//}