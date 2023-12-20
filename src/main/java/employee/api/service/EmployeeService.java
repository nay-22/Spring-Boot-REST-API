package employee.api.service;

import static java.time.LocalTime.now;
import static java.util.Map.of;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

import employee.api.domain.EmailDetails;
import employee.api.domain.Employee;
import employee.api.domain.HttpResponse;
import employee.api.repository.EmployeeRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepo repo;

    private final AmazonDynamoDB client;

    private final EmailService emailService;

    public Employee save(Employee employee) {
        Employee emp = repo.save(employee);
        if (emp != null) {
            String managerEmail = repo.getEmployeeById(emp.getReportsTo()).getEmail();
            String employeeName = emp.getName();
            String phoneNumber = emp.getPhone();
            String emailId = emp.getEmail();
            
            emailService.sendEmail(
                EmailDetails.builder()
                .recipient(managerEmail)
                .msgBody(String.format("%s will now work under you. Mobile number is +91 %s and email is %s", employeeName, phoneNumber, emailId))
                .subject("New Employee Added")
                .build()
            );
        }
        return emp;
    }

    public Employee getEmployeeById(String id) {
        return repo.getEmployeeById(id);
    }

    public List<Employee> getEmployees() {
        return repo.getEmployees();
    }

    public void delete(String id) {
        repo.delete(id);
    }

    public Employee update(String id, Employee employee) {
        return repo.update(id, employee);
    }

    public Employee getNthLevelManager(Employee employee, int level) {
        if (level < 0) {
            return null;
        }
        String reportsTo = employee.getReportsTo();
        while (level > 0 && reportsTo != null) {
            employee = repo.getEmployeeById(reportsTo);
            reportsTo = employee.getReportsTo();
            level--;
        }
        return (level == 0) ? employee : null;
    }

    public ResponseEntity<HttpResponse> getEmployees(
            int pageSize,
            String lastEvaluatedKeyString,
            String sortBy,
            String orderBy
    ) {
        System.out.println("Page Size = " + pageSize);
        System.out.println("Last Evaluated Key = " + lastEvaluatedKeyString);
        System.out.println("Sort By = " + sortBy);
        System.out.println("Order By = " + orderBy);
        ScanRequest scanRequest = new ScanRequest()
                .withTableName("Employee")
                .withLimit(pageSize);

        
        // Adjust the exclusive start key based on the page number, which in this case is the last evaluated key
        if (lastEvaluatedKeyString.equals(""))
            scanRequest.withExclusiveStartKey(null);
        else
            scanRequest.withExclusiveStartKey(Map.of("id", new AttributeValue().withS(lastEvaluatedKeyString)));

        ScanResult scanResult = client.scan(scanRequest);
        
        Map<String, AttributeValue> lastEvaluatedKey = scanResult.getLastEvaluatedKey();
        // System.out.println(lastEvaluatedKey.toString());
        lastEvaluatedKeyString = lastEvaluatedKey.get("id").getS().toString();

        List<Map<String, AttributeValue>> employeesAttributes = scanResult.getItems();
        List<Employee> employees = convertToEmployees(employeesAttributes);

        if (sortBy.equalsIgnoreCase("name") || sortBy.equalsIgnoreCase("email")) {
            sortEmployees(employees, sortBy, orderBy);
        }
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("lastEvaluatedKey", lastEvaluatedKeyString,"employees", employees))
                        // .data(of("lastEvaluatedKey", lastEvaluatedKeyString,"employees", employees))
                        .message("Employee List")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    private void sortEmployees(List<Employee> employees, String sortBy, String orderBy) {
        Comparator<Employee> comparator;
        switch (sortBy.toLowerCase().strip()) {
            case "name":
                comparator = Comparator.comparing(Employee::getName);
                break;
            case "email":
                comparator = Comparator.comparing(Employee::getEmail);
                break;
            default:
                return;
        }
        if ("desc".equalsIgnoreCase(orderBy)) {
            comparator = comparator.reversed();
        }
        Collections.sort(employees, comparator);
    }

    private List<Employee> convertToEmployees(List<Map<String, AttributeValue>> employeesAttributes) {
        List<Employee> employees = new ArrayList<>();

        for (Map<String, AttributeValue> attributeMap : employeesAttributes) {
            Employee employee = new Employee();
            employee.setId(attributeMap.get("id").getS());
            if (attributeMap.containsKey("name")) {
                employee.setName(attributeMap.get("name").getS());
            } if (attributeMap.containsKey("phone")) {
                employee.setPhone(attributeMap.get("phone").getS());
            } if (attributeMap.containsKey("email")) {
                employee.setEmail(attributeMap.get("email").getS());
            } if (attributeMap.containsKey("reportsTo")) {
                employee.setReportsTo(attributeMap.get("reportsTo").getS());
            } if (attributeMap.containsKey("profileImage")) {
                employee.setProfileImage(attributeMap.get("profileImage").getS());
            }
            employees.add(employee);
        }
        return employees;
    }
    
}
