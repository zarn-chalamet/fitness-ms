package com.fitness.activity_service.service;

import com.fitness.activity_service.dto.ActivityRequest;
import com.fitness.activity_service.dto.ActivityResponse;

import java.util.List;

public interface ActivityService {
    ActivityResponse trackActivity(ActivityRequest activityRequest);

    List<ActivityResponse> getUserActivities(Long userId);

    ActivityResponse getActivityById(String activityId);
}
