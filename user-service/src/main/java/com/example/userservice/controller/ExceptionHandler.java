package com.example.userservice.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeoutException;

@ControllerAdvice
public class ExceptionHandler {


    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseBody
    public String handleTimeOutException(TimeoutException exception){
        return exception.getMessage();
    }

}
