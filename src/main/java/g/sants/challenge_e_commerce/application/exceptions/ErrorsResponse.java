package g.sants.challenge_e_commerce.application.exceptions;

import java.time.LocalDateTime;

public class ErrorsResponse {
    private LocalDateTime errorTime;
    private int status;
    private String error;
    private String message;

    public ErrorsResponse(LocalDateTime errorTime, int status, String error, String message) {
        this.errorTime = errorTime;
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public LocalDateTime getErrorTime() {
        return errorTime;
    }

    public void setErrorTime(LocalDateTime errorTime) {
        this.errorTime = errorTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
