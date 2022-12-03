package com.guardjo.freeworkslackbot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class FreeWorkControllerAdvice {
    @ExceptionHandler(NullPointerException.class)
    protected String handleNullPointerException(NullPointerException e) {
        log.error("Object is Null, ", e);

        return "Not Found Data";
    }

    @ExceptionHandler(Exception.class)
    protected String handleDefaultException(Exception e) {
        log.error("Error : ", e);

        return "Error : " + e.getMessage();
    }
}
