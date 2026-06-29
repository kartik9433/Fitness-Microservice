package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.model.Recommendation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener {

    private final ActivityAIService activityAIService;
    private final RecommendationService recommendationService;

    @RabbitListener(queues = "activity.queue")
    public void processActivity(Activity activity) {
        log.info("Received activity for userId: {}", activity.getUserId());
        log.info("Calories Burned: {}", activity.getCaloriesBurned());

        Recommendation recommendation = activityAIService.generateRecommendation(activity);
        recommendationService.saveRecommendation(recommendation);

        log.info("Recommendation saved for activityId: {}", activity.getId());
    }
}