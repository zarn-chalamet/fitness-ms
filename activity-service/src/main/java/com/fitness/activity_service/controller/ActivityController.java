package com.fitness.activity_service.controller;

import com.fitness.activity_service.dto.ActivityRequest;
import com.fitness.activity_service.dto.ActivityResponse;
import com.fitness.activity_service.service.ActivityService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@AllArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @PostMapping
    public ResponseEntity<ActivityResponse> trackActivity(@RequestBody ActivityRequest activityRequest) {
        ActivityResponse activityResponse = activityService.trackActivity(activityRequest);

        return ResponseEntity.ok(activityResponse);
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getAllActivitiesByUserId(@RequestHeader("X-User-ID") String userId) {
        List<ActivityResponse> responses = activityService.getUserActivities(userId);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{activityId}")
    public ResponseEntity<ActivityResponse> getActivityById(@PathVariable String activityId) {
        ActivityResponse activityResponse = activityService.getActivityById(activityId);

        return ResponseEntity.ok(activityResponse);
    }
}
