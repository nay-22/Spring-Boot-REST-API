package employee.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

@Configuration
public class ConfigDynamoDB {

    @Bean
    public DynamoDBMapper dynamoDBMapper() {
        return new DynamoDBMapper(buildDynamoDBClient());
    }

    @Bean AmazonDynamoDB amazonDynamoDB() {
        return buildDynamoDBClient();
    }
    
    private AmazonDynamoDB buildDynamoDBClient() {
        return AmazonDynamoDBClientBuilder
            .standard()
            .withEndpointConfiguration(
                new AwsClientBuilder.EndpointConfiguration(
                    "dynamodb.YOUR_REGION.amazonaws.com",
                    "YOUR_REGION"
                )
            )
            .withCredentials(
                new AWSStaticCredentialsProvider(
                    new BasicAWSCredentials(
                        "YOUR_ACCESS_KEY",
                        "YOUR_SECRET_ACCESS_KEY")
                )
            )
            .build();
    }
}
