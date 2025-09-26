package vn.hoidanit.jobhunter.util.error;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import vn.hoidanit.jobhunter.domain.RestResponse;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler({ IdInvalidException.class,
            UsernameNotFoundException.class })
    public ResponseEntity<RestResponse<Object>> handleIdInvalidException(IdInvalidException ex) {
        RestResponse<Object> rest = new RestResponse<>();
        rest.setStatusCode(HttpStatus.BAD_REQUEST.value());
        rest.setError(ex.getMessage());
        rest.setMessage("IdInvalidException");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(rest);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<Object>> handleValidation(MethodArgumentNotValidException ex) {
        RestResponse<Object> rest = new RestResponse<>();
        rest.setStatusCode(HttpStatus.BAD_REQUEST.value());

        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }

        rest.setError(ex.getBody().getDetail());
        rest.setMessage(errors.size() > 1 ? errors : errors.get(0).toString());

        return ResponseEntity.badRequest().body(rest);
    }
}
