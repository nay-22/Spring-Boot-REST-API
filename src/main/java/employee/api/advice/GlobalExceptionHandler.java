package employee.api.advice;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;

import employee.api.domain.HttpResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<HttpResponse> handleNullPointerException(NullPointerException e) {
        System.err.println(e.getMessage());
        return buildErrorResponse(HttpStatus.NOT_FOUND, "The given id does not exists");
    }

    @ExceptionHandler(ConditionalCheckFailedException.class)
    public ResponseEntity<HttpResponse> handleConditionalCheckFailedException(ConditionalCheckFailedException e) {
        System.err.println(e.getMessage());
        return buildErrorResponse(HttpStatus.NOT_FOUND, "The given id does not exists");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<HttpResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Invalid request parameter type");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpResponse> handleValidException(MethodArgumentNotValidException e) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Missing body attribute");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HttpResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Invalid Http message");
    }

    private ResponseEntity<HttpResponse> buildErrorResponse(HttpStatus status, String message) {
        HttpResponse response = HttpResponse.builder()
                .timeStamp(LocalDateTime.now().toString())
                .statusCode(status.value())
                .status(status)
                .message(message)
                .build();
        return new ResponseEntity<>(response, status);
    }
}
