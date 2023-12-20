package employee.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import employee.api.domain.Employee;
import employee.api.domain.HttpResponse;
import employee.api.service.EmployeeService;
import lombok.RequiredArgsConstructor;

import static java.time.LocalTime.now;
import static java.util.Map.of;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/employee")
public class EmployeeController {

    private final EmployeeService service;

    @PostMapping("/save")
    public ResponseEntity<HttpResponse> saveEmployee(@RequestBody Employee emp) {
        Employee e = service.save(emp);
        URI location = getURI(e.getId());
        return ResponseEntity.created(location).body(
            HttpResponse.builder()
            .timeStamp(now().toString())
            .data(of("Created", e, "location", location))
            .message("Item created successfully")
            .status(HttpStatus.CREATED)
            .statusCode(HttpStatus.CREATED.value())
            .build()
        );
    }

    private URI getURI(String id) {
        return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/employee/get/" + id).toUriString());
    }

    @GetMapping("get/{id}")
    public ResponseEntity<HttpResponse> getEmployee(@PathVariable("id") String id) {
        Employee employee = service.getEmployeeById(id);
        return ResponseEntity.ok().body(
            HttpResponse.builder()
            .timeStamp(now().toString())
            .data(of("employee", employee))
            .message("Single Record Fetch")
            .status(HttpStatus.OK)
            .statusCode(HttpStatus.OK.value())
            .build()
        );
    }

    @GetMapping("/get")
    public ResponseEntity<HttpResponse> getEmployeesWithPagination(
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "") String lastEvaluatedKeyString,
            @RequestParam(defaultValue = "") String sortBy,
            @RequestParam(defaultValue = "asc") String orderBy) {

        System.out.println("Page Size = " + pageSize);
        return service.getEmployees(pageSize, lastEvaluatedKeyString, sortBy, orderBy);
    }


    @GetMapping("/get/all") // Modified the mapping to /get/all
    public ResponseEntity<HttpResponse> getAllEmployees() {
        List<Employee> employees = service.getEmployees();
        return ResponseEntity.ok().body(
            HttpResponse.builder()
            .timeStamp(now().toString())
            .data(of("employees", employees))
            .message("Employee List")
            .status(HttpStatus.OK)
            .statusCode(HttpStatus.OK.value())
            .build()
        );
    }


    @GetMapping("/getManager/{employeeId}/{level}")
    public ResponseEntity<HttpResponse> getNthLevelManager(
            @PathVariable("employeeId") String employeeId,
            @PathVariable("level") int level) {

        Employee employee = service.getEmployeeById(employeeId);
        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                HttpResponse.builder()
                .timeStamp(now().toString())
                .message("Employee not found with ID: " + employeeId)
                .status(HttpStatus.NOT_FOUND)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build()
            );
        }
        Employee manager = service.getNthLevelManager(employee, level);
        if (manager == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                HttpResponse.builder()
                .timeStamp(now().toString())
                .message("Manager not found for employee ID: " + employeeId + " at level " + level)
                .status(HttpStatus.NOT_FOUND)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build()
            );
        }
        return ResponseEntity.ok().body(
            HttpResponse.builder()
            .timeStamp(now().toString())
            .data(of("manager", manager))
            .message("Manager found successfully")
            .status(HttpStatus.OK)
            .statusCode(HttpStatus.OK.value())
            .build()
        );
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<HttpResponse> deleteEmployee(@PathVariable("id") String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("update/{id}")
    public ResponseEntity<HttpResponse> updateEmployee(@PathVariable("id") String id, @RequestBody Employee employee) {
        employee.setId(id);
        Employee emp = service.update(id, employee);
        return ResponseEntity.ok().body(
            HttpResponse.builder()
            .timeStamp(now().toString())
            .data(of("updated_employee", emp))
            .message("Updated employee successfully")
            .status(HttpStatus.OK)
            .statusCode(HttpStatus.OK.value())
            .build()
        );
    }
}
