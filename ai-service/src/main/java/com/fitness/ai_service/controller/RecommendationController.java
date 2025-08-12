package com.fitness.ai_service.controller;

import com.fitness.ai_service.model.Recommendation;
import com.fitness.ai_service.service.RecommendationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@AllArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Recommendation>> getUserRecommendations(@PathVariable String userId) {
        List<Recommendation> recommendations = recommendationService.getUserRecommendations(userId);

        return ResponseEntity.ok(recommendations);
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<Recommendation> getRecommendationByActivityId(@PathVariable String activityId) {
        Recommendation recommendation = recommendationService.getRecommendationByActivityId(activityId);

        return ResponseEntity.ok(recommendation);
    }
}
