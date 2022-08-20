package org.shiv.razorpay.exception;

import com.razorpay.RazorpayException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<?> handleGenericException(GenericException genericException){
        log.error(genericException.toString());
        return ResponseEntity.status(genericException.getStatusCode()).body(genericException.getExceptionDesc());
    }

    @ExceptionHandler(RazorpayException.class)
    public ResponseEntity<?> handleRazorpayException(RazorpayException razorpayException){
        log.error(razorpayException.getMessage());
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("Error has occurred Please try again");
    }
}
