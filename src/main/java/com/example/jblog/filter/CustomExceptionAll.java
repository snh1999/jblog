package com.example.jblog.filter;

import com.example.jblog.filter.dto.MyExceptionResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.Optional;

@ControllerAdvice
public class CustomExceptionAll {
    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseStatusException handleError1(HttpServletRequest req, Exception ex) {

        String msg = null;
        if (ex.getCause().getCause() instanceof ConstraintViolationException e) {
            Optional<ConstraintViolation<?>> optional = e.getConstraintViolations().stream().findFirst();
            msg = optional.isPresent() ? optional.get().getMessageTemplate() : ex.getMessage();
        }

        return new ResponseStatusException (HttpStatus.BAD_REQUEST, msg, ex);
    }

    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseStatusException handleError2(HttpServletRequest req, Exception ex) {

        String msg = ex.getMessage();
        if (ex.getCause().getCause() instanceof SQLException e) {
            if (e.getMessage().contains("Key")) {
                msg = e.getMessage().substring(e.getMessage().indexOf("Key"));
            }
        }
        return new ResponseStatusException(HttpStatus.CONFLICT, msg, ex);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        String message = ex.getMessage();
        MyExceptionResponse errorResponse = new MyExceptionResponse(HttpStatus.NOT_FOUND, message);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseStatusException handleEntityNotFoundException(EntityNotFoundException ex) {
        String msg = "Entity Not Found";
        return new ResponseStatusException(HttpStatus.NOT_FOUND, msg, ex);
    }
    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseStatusException handleLogInError(AuthenticationCredentialsNotFoundException ex) {
        System.out.println("Here");
        String msg = ex.getMessage();
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, msg, ex);
    }
    @ExceptionHandler(DisabledException.class)
    public ResponseStatusException accountDisabledException(DisabledException ex) {
        String msg = "Your account is not enabled. Check your Email or enter again to get your email";
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, msg, ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseStatusException handleException(Exception ex) {
//        ex.printStackTrace();
        String msg = "Internal Server Error" + ex.getMessage();
        return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, msg, ex);
    }

}
