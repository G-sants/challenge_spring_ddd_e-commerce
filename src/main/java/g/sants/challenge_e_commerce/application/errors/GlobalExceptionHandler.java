package g.sants.challenge_e_commerce.application.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public static ResponseEntity<ErrorsResponse> handleBadCredentialsException
            (BadCredentialsException be){
        ErrorsResponse errorsResponse = new ErrorsResponse(LocalDateTime.now(), HttpStatus.UNAUTHORIZED.value(),
                "Invalid Login", be.getMessage());
        return new ResponseEntity<>(errorsResponse,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public static ResponseEntity<ErrorsResponse> handleLoginException
            (Exception e){
        ErrorsResponse errorsResponse = new ErrorsResponse(LocalDateTime.now(), HttpStatus.FORBIDDEN.value(),
                "Authentication Failed", e.getMessage());
        return new ResponseEntity<>(errorsResponse,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public static ResponseEntity<ErrorsResponse> userNofFoundException( RuntimeException re){
        ErrorsResponse errorsResponse = new ErrorsResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
                "User not Found with ID", re.getMessage());
        return new ResponseEntity<>(errorsResponse,HttpStatus.NOT_FOUND);
    }
}
