package com.fitness.activityservice.controller;

import com.fitness.activityservice.model.ActivityRequest;
import com.fitness.activityservice.model.ActivityResponse;
import com.fitness.activityservice.service.ActivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activites")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping("/request")
    public ResponseEntity<ActivityResponse>tractActivity(@RequestBody ActivityRequest request){
        return ResponseEntity.ok(activityService.tractActivity(request));
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponse>>getUserActivity(@RequestHeader("userId") String userId){
        return ResponseEntity.ok(activityService.getUserActivity(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityResponse>getActivityById(@PathVariable String id){
        return ResponseEntity.ok(activityService.getActivityById(id));
    }

}
