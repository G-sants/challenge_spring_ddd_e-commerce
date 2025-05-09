package g.sants.challenge_e_commerce.application.exceptions;

import g.sants.challenge_e_commerce.application.exceptions.errors.LoginException;
import g.sants.challenge_e_commerce.application.exceptions.errors.LoginFailedException;
import g.sants.challenge_e_commerce.application.exceptions.errors.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(LoginException.class)
    private static ResponseEntity<ErrorsResponse> handleLoginException
            (LoginException exception){
        ErrorsResponse errorsResponse = new ErrorsResponse(LocalDateTime.now(), HttpStatus.UNAUTHORIZED.value(),
                "Invalid Login",exception.getMessage());
        return new ResponseEntity<>(errorsResponse,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(LoginFailedException.class)
    public static ResponseEntity<ErrorsResponse> handleLoginException
            (LoginFailedException exception){
        ErrorsResponse errorsResponse = new ErrorsResponse(LocalDateTime.now(), HttpStatus.FORBIDDEN.value(),
                "Authentication Failed",exception.getMessage());
        return new ResponseEntity<>(errorsResponse,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public static ResponseEntity<ErrorsResponse> userNotFoundException(UserNotFoundException exception){
        ErrorsResponse errorsResponse = new ErrorsResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
                "User not Found with the ID given", exception.getMessage());
        return new ResponseEntity<>(errorsResponse,HttpStatus.NOT_FOUND);
    }

}
