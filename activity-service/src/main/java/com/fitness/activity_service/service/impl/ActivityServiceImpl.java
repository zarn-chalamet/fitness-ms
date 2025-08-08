package com.fitness.activity_service.service.impl;

import com.fitness.activity_service.dto.ActivityRequest;
import com.fitness.activity_service.dto.ActivityResponse;
import com.fitness.activity_service.exception.ActivityNotFoundException;
import com.fitness.activity_service.mapper.ActivityMapper;
import com.fitness.activity_service.model.Activity;
import com.fitness.activity_service.repository.ActivityRepository;
import com.fitness.activity_service.service.ActivityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    @Override
    public ActivityResponse trackActivity(ActivityRequest activityRequest) {

        Activity newActivity = new Activity();
        newActivity.setUserId(activityRequest.getUserId());
        newActivity.setType(activityRequest.getType());
        newActivity.setDuration(activityRequest.getDuration());
        newActivity.setCaloriesBurned(activityRequest.getCaloriesBurned());
        newActivity.setStartTime(activityRequest.getStartTime());
        newActivity.setAdditionalMetrics(activityRequest.getAdditionalMetrics());

        Activity savedActivity = activityRepository.save(newActivity);

        return ActivityMapper.toActivityDto(savedActivity);
    }

    @Override
    public List<ActivityResponse> getUserActivities(String userId) {

        List<Activity> activities = activityRepository.findByUserId(userId);

        return activities.stream()
                .map(ActivityMapper::toActivityDto)
                .collect(Collectors.toList());
    }

    @Override
    public ActivityResponse getActivityById(Long activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(()-> new ActivityNotFoundException("Activity not found with ID: " + activityId));
        return ActivityMapper.toActivityDto(activity);
    }
}
