package com.fitness.activity_service.exception;

public class ActivityNotFoundException extends RuntimeException{

    public ActivityNotFoundException(String message){
        super(message);
    }
}
