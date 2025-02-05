package com.workintech.zoo.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ZooGlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ZooGlobalExceptionHandler.class);

    @ExceptionHandler(ZooException.class)
    public ResponseEntity<ZooErrorResponse> handleZooException(ZooException exception) {
        ZooErrorResponse zooErrorResponse = new ZooErrorResponse(
                exception.getHttpStatus().value(),
                exception.getLocalizedMessage(),
                System.currentTimeMillis());
        log.error("Exception occurred: ", exception);
        return new ResponseEntity<>(zooErrorResponse, exception.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ZooErrorResponse> handleGenericException(Exception exception) {
        ZooErrorResponse zooErrorResponse = new ZooErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getLocalizedMessage(),
                System.currentTimeMillis());
        log.error("Unhandled exception occurred: ", exception);
        return new ResponseEntity<>(zooErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
