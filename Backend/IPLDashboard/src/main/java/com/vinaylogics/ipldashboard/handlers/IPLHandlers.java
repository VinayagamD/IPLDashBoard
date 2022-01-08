package com.vinaylogics.ipldashboard.handlers;

import com.vinaylogics.ipldashboard.dtos.ErrorDto;
import com.vinaylogics.ipldashboard.exceptions.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class IPLHandlers {

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorDto handleNotFound(DataNotFoundException exception){
        log.info("Data Not Found {}",exception.getMessage());
        return ErrorDto.builder().message(exception.getMessage()).status(HttpStatus.NOT_FOUND.value()).build();
    }
}
