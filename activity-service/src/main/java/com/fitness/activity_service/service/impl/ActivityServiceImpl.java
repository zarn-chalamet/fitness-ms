package com.fitness.activity_service.service.impl;

import com.fitness.activity_service.dto.ActivityRequest;
import com.fitness.activity_service.dto.ActivityResponse;
import com.fitness.activity_service.exception.ActivityNotFoundException;
import com.fitness.activity_service.mapper.ActivityMapper;
import com.fitness.activity_service.model.Activity;
import com.fitness.activity_service.repository.ActivityRepository;
import com.fitness.activity_service.service.ActivityService;
import com.fitness.activity_service.service.UserValidationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.queues.name:}")
    private String queue;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Override
    public ActivityResponse trackActivity(ActivityRequest activityRequest) {

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(activityRequest.getUserId());
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
        boolean isValidUser = userValidationService.validateUserWithRestClient(activityRequest.getUserId());
//        boolean isValidUser = userValidationService.validateUser(activityRequest.getUserId());
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(isValidUser);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
        if (!isValidUser) {
            throw new RuntimeException("Invalid User: "+ activityRequest.getUserId());
        }

        Activity newActivity = new Activity();
        newActivity.setUserId(activityRequest.getUserId());
        newActivity.setType(activityRequest.getType());
        newActivity.setDuration(activityRequest.getDuration());
        newActivity.setCaloriesBurned(activityRequest.getCaloriesBurned());
        newActivity.setStartTime(activityRequest.getStartTime());
        newActivity.setAdditionalMetrics(activityRequest.getAdditionalMetrics());

        Activity savedActivity = activityRepository.save(newActivity);

        //send message to rabbitmq
        try{
            rabbitTemplate.convertAndSend(exchange,routingKey,savedActivity);
        }catch (Exception e) {
            log.error("Failed to publish activity to RabbitMq",e);
        }

        return ActivityMapper.toActivityDto(savedActivity);
    }

    @Override
    public List<ActivityResponse> getUserActivities(Long userId) {

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
