package employee.api.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;

import employee.api.domain.Employee;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmployeeRepo {

    @Autowired
    private DynamoDBMapper dbMapper;

    private final AmazonDynamoDB client;

    public Employee save(Employee employee) {
        dbMapper.save(employee);
        return employee;
    }

    public Employee getEmployeeById(String id) {
        return dbMapper.load(Employee.class, id);
    }

    public List<Employee> getEmployees() {
        return dbMapper.scan(Employee.class, new DynamoDBScanExpression());
    }


    public List<Map<String, AttributeValue>> getEmployeesWithPagination(int page, int pageSize) {
        // Adjust the table name and key conditions accordingly
        QueryRequest queryRequest = new QueryRequest()
                .withTableName("YourTableName")
                .withLimit(pageSize)
                .withExclusiveStartKey(null) // Set this to start from the last evaluated key if paginating
                .withScanIndexForward(true) // Change to false for descending order
                .withKeyConditionExpression("#sortKey = :sortValue")
                .withExpressionAttributeNames(Map.of("#sortKey", "YourSortKey"))
                .withExpressionAttributeValues(Map.of(":sortValue", new AttributeValue().withS(""))); // Replace with actual value

        QueryResult queryResponse = client.query(queryRequest);

        return queryResponse.getItems();
    }



    public void delete(String id) {
        try {
            Employee emp = getEmployeeById(id);
            dbMapper.delete(emp);
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
    }

    public Employee update(String id, Employee employee) {
        try {
            dbMapper.save(employee, new DynamoDBSaveExpression()
                    .withExpectedEntry("id", 
                        new ExpectedAttributeValue(
                            new AttributeValue().withS(id)
                        )
                    )
                );
            return getEmployeeById(id);
        } catch (ConditionalCheckFailedException e) {
            System.err.println(e.getMessage());
            throw new ConditionalCheckFailedException(e.getMessage());
        }
    }

}
