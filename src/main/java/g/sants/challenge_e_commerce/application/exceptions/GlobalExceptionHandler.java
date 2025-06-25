package g.sants.challenge_e_commerce.application.exceptions;

import g.sants.challenge_e_commerce.application.exceptions.errors.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ErrorsResponse> handleLoginException
            (LoginException exception){
        ErrorsResponse errorsResponse = new ErrorsResponse(LocalDateTime.now(), HttpStatus.UNAUTHORIZED.value(),
                "Invalid Login",exception.getMessage());
        return new ResponseEntity<>(errorsResponse,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<ErrorsResponse> handleLoginException
            (LoginFailedException exception){
        ErrorsResponse errorsResponse = new ErrorsResponse(LocalDateTime.now(), HttpStatus.FORBIDDEN.value(),
                "Authentication Failed",exception.getMessage());
        return new ResponseEntity<>(errorsResponse,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorsResponse> userNotFoundException(UserNotFoundException exception){
        ErrorsResponse errorsResponse = new ErrorsResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
                "User not Found with the ID given", exception.getMessage());
        return new ResponseEntity<>(errorsResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorsResponse> itemNotFoundException(ItemNotFoundException exception){
        ErrorsResponse errorsResponse = new ErrorsResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
                "The item does not exist or is missing in the stock", exception.getMessage());
        return new ResponseEntity<>(errorsResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorsResponse> orderNotFoundException(OrderNotFoundException exception){
        ErrorsResponse errorsResponse = new ErrorsResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
                "This order does not exist or was already cancelled", exception.getMessage());
        return new ResponseEntity<>(errorsResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderCancelledException.class)
    public ResponseEntity<ErrorsResponse> orderCancelledException(OrderCancelledException exception){
        ErrorsResponse errorsResponse = new ErrorsResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
                "This order was already cancelled by the user", exception.getMessage());
        return new ResponseEntity<>(errorsResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WrongItemEntryException.class)
    public ResponseEntity<ErrorsResponse> wrongItemEntryException(WrongItemEntryException exception){
        ErrorsResponse errorsResponse = new ErrorsResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
                "You probably mistyped an entry to a Item", exception.getMessage());
        return new ResponseEntity<>(errorsResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoInfoReceivedException.class)
    public ResponseEntity<ErrorsResponse> noInfoReceivedException(NoInfoReceivedException exception){
        ErrorsResponse errorsResponse = new ErrorsResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
                "The service returned a null response to the request", exception.getMessage());
        return new ResponseEntity<>(errorsResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RegistrationAlreadyDoneException.class)
    public ResponseEntity<ErrorsResponse> userAlreadyRegisteredException(RegistrationAlreadyDoneException exception){
        ErrorsResponse errorsResponse = new ErrorsResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
                "This email has already been used to register an user", exception.getMessage());
        return new ResponseEntity<>(errorsResponse,HttpStatus.NOT_FOUND);
    }
}
