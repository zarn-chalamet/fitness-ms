package com.fitness.ai_service.exception;

public class RecommendationNotFoundException extends RuntimeException{

    public RecommendationNotFoundException(String message) {
        super(message);
    }
}
