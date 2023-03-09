package ru.fortushin.tvc.util;

public class EmployeeErrorResponse {
    private String message;

    public EmployeeErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
