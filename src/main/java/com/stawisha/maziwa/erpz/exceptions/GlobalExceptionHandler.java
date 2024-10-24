package com.stawisha.maziwa.erpz.exceptions;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import org.apache.log4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * This file is a global Restful exception handler
 *
 * @author Samuel Maina Create 11/11/23
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * An exception handler for a TenantNotFoundException
     *
     * @return ResponseEntity object with error message and error code 404
     */
    @ExceptionHandler(TenantNotFoundException.class)
    public ResponseEntity<?> tenantNotFoundException(TenantNotFoundException ex) {
        
        return new ResponseEntity<>(new Error(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     * An exception handler for a EmployeeNotFoundException
     *
     * @return ResponseEntity object with error message and error code 404
     */
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<?> employeeNotFoundException(EmployeeNotFoundException ex) {
        return new ResponseEntity<>(new Error(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     * An exception handler for a MemberNotFoundException
     *
     * @return ResponseEntity object with error message and error code 404
     */
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<?> memberNotFoundException(MemberNotFoundException ex) {
        return new ResponseEntity<>(new Error(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     * An exception handler for a EmployeeAlreadyexistsException
     *
     * @return ResponseEntity object with error message and error code 409
     */
    @ExceptionHandler(EmployeeAlreadyExistsException.class)
    public ResponseEntity<?> employeeAlreadyExistsException(EmployeeAlreadyExistsException ex) {
        MDC.put("user", ex.getMessage());
        return new ResponseEntity<>(new Error(ex.getMessage()), HttpStatus.CONFLICT);
    }

    /**
     * An exception handler for a MemberExistsException
     *
     * @return ResponseEntity object with error message and error code 409
     */
    @ExceptionHandler(MemberExistsException.class)
    public ResponseEntity<?> memberExistsException(MemberExistsException ex) {
        return new ResponseEntity<>(new Error(ex.getMessage()), HttpStatus.CONFLICT);
    }

    /**
     * An exception handler for a RecordExistsException
     *
     * @return ResponseEntity object with error message and error code 409
     */
    @ExceptionHandler(RecordExistsException.class)
    public ResponseEntity<?> recordExistsException(RecordExistsException ex) {
        return new ResponseEntity<>(ex, HttpStatus.CONFLICT);
    }

    /**
     * An exception handler for a FutureDateException
     *
     * @return ResponseEntity object with error message and error code 409
     */
    @ExceptionHandler(FutureDateException.class)
    public ResponseEntity<?> futureDateException() {
        return new ResponseEntity<>("The record failed to insert a future date", HttpStatus.CONFLICT);
    }

    /**
     * Exception handler for SQLIntegrityConstraintViolationException
     *
     * @return exception body, HttpStatus.FORBIDDEN
     */
    //@ExceptionHandler(java.sql.SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<?> sQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {

        return new ResponseEntity<>(new Error(ex.getMessage()), HttpStatus.FORBIDDEN);
    }

    /**
     * Exception handler for SQLIntegrityConstraintViolationException
     *
     * @return exception body, HttpStatus.FORBIDDEN
     */
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<?> accountDisabled(DisabledException ex) {

        return new ResponseEntity<>(new Error(ex.getMessage()), HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * Exception handler for SQLIntegrityConstraintViolationException
     *
     * @return exception body, HttpStatus.FORBIDDEN
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> invalidUsernameAndPassword(BadCredentialsException ex) {

        return new ResponseEntity<>(new Error(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }
    
     /**
     * An exception handler for a RecordNotFoundException
     *
     * @return ResponseEntity object with error message and error code 404
     */
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<?> recordNotFoundException(RecordNotFoundException ex) {
        return new ResponseEntity<>(new Error(ex.getMessage()), HttpStatus.NOT_FOUND);
    }
    
         /**
     * An exception handler for a LockedException
     *
     * @return ResponseEntity object with error message and error code 404
     */
    @ExceptionHandler(LockedException.class)
    public ResponseEntity<?> accountLockedException(LockedException ex) {
        
        return new ResponseEntity<>(new Error(ex.getMessage()), HttpStatus.FORBIDDEN);
    }
   
} class Error{
String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Error(String message) {
        this.message = message;
    }

    
    
    

}
