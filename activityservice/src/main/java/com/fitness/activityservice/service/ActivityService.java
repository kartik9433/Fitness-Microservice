package com.fitness.activityservice.service;

import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.model.ActivityRequest;
import com.fitness.activityservice.model.ActivityResponse;
import com.fitness.activityservice.repository.ActivityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public ActivityResponse tractActivity(ActivityRequest request) {
        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMetrics())
                .build();
       Activity newactivity  = activityRepository.save(activity);
        return mapActivityToActivityResponse(newactivity);
    }
    public ActivityResponse mapActivityToActivityResponse(Activity  activity){
         ActivityResponse activityResponse = new  ActivityResponse();
         activityResponse.setId(activity.getId());
         activityResponse.setUserId(activity.getUserId());
         activityResponse.setType(activity.getType());
         activityResponse.setDuration(activity.getDuration());
         activityResponse.setCaloriesBurned(activity.getCaloriesBurned());
         activityResponse.setStartTime(activity.getStartTime());
         activityResponse.setCreatedAt(activity.getCreatedAt());
         activityResponse.setUpdatedAt(activity.getUpdatedAt());
         activityResponse.setAdditionalMetrics(activity.getAdditionalMetrics());
         return activityResponse;
    }

    public List<ActivityResponse> getUserActivity(String userId) {
       List<Activity> activities =  activityRepository.findByUserId(userId);
       return activities.stream().
               map(this::mapActivityToActivityResponse)
               .collect(Collectors.toList());
    }

    public ActivityResponse getActivityById(String activityId) {
        return  activityRepository.findById(activityId)
                .map(this::mapActivityToActivityResponse)
                .orElseThrow(()->new RuntimeException("Activity does not exits"));
    }

}
