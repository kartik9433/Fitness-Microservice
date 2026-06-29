package com.fitness.activityservice.service;

import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.model.ActivityRequest;
import com.fitness.activityservice.model.ActivityResponse;
import com.fitness.activityservice.repository.ActivityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;
    private final RabbitTemplate rabbitTemplate;

    public ActivityService(ActivityRepository activityRepository, UserValidationService userValidationService, RabbitTemplate rabbitTemplate) {
        this.activityRepository = activityRepository;
        this.userValidationService = userValidationService;
        this.rabbitTemplate = rabbitTemplate;
    }
    @Value("${rabbitmq.queue.name}")
    private String queue;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;


    public ActivityResponse tractActivity(ActivityRequest request) {

         boolean isValidUser = userValidationService.validateUser(request.getUserId());

         if(!isValidUser){
             throw new RuntimeException("Invalid user"+request.getUserId());
         }

        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMetrics())
                .build();
       Activity newactivity  = activityRepository.save(activity);

       try{
            rabbitTemplate.convertAndSend(exchange,routingKey,newactivity);
       }
       catch (Exception e){
          log.error("Failed to publish activity to RabbitMQ: ",e);
       }
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
