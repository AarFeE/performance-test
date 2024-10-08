package riwi.aarfee.performance_test.dtos.responses;

import org.springframework.http.HttpStatus;

public class ErrorRes {
    private String message;
    private HttpStatus status;

    public ErrorRes(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
