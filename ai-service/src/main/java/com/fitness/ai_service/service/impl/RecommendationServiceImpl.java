package com.fitness.ai_service.service.impl;

import com.fitness.ai_service.exception.RecommendationNotFoundException;
import com.fitness.ai_service.model.Recommendation;
import com.fitness.ai_service.repository.RecommendationRepository;
import com.fitness.ai_service.service.RecommendationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;

    @Override
    public List<Recommendation> getUserRecommendations(Long userId) {
        return recommendationRepository.findByUserId(userId);
    }

    @Override
    public Recommendation getRecommendationByActivityId(String activityId) {
        return recommendationRepository.findByActivityId(activityId)
                .orElseThrow(()-> new RecommendationNotFoundException("Recommendation Not Found with activity ID: "+ activityId));

    }
}
