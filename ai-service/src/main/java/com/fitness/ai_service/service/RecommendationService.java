package com.fitness.ai_service.service;

import com.fitness.ai_service.model.Recommendation;

import java.util.List;

public interface RecommendationService {
    List<Recommendation> getUserRecommendations(Long userId);

    Recommendation getRecommendationByActivityId(String activityId);
}
