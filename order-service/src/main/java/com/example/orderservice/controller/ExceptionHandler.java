package com.example.orderservice.controller;

import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseBody
    public String handleIllegalArgumentException(IllegalArgumentException exception){
        return exception.getMessage();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseBody
    public String handleRuntimeException(RuntimeException exception){return exception.getMessage();}
}
