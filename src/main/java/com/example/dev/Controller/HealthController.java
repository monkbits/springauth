package com.example.dev.Controller;

import com.example.dev.Entities.WeightLog;
import com.example.dev.service.WeightLogService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.aspectj.weaver.TypeVariableReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/health")
public class HealthController {
    @Autowired
    public RedisTemplate redisTemplate;

    @Autowired
    public WeightLogService weightLogService;

    @Autowired
    public Gson gson;

    @RequestMapping(value = "/get_weight_logs", method = {RequestMethod.GET, RequestMethod.POST})
    public List<WeightLog> getWeightLog(){
        List<WeightLog> wls = weightLogService.getWeightLogs();
        redisTemplate.opsForValue().set("weightLogs",gson.toJson(wls));
        return wls;
    }

    @PostMapping("/add_weight_log")
    public ResponseEntity<String> addWeightLog(@RequestBody WeightLog weightLog) {
        if(weightLogService.addWeightLog(weightLog))
            return ResponseEntity.ok("Saved successfully");
        else
            return ResponseEntity.ok("Oops! something got broken");
    }


    @RequestMapping(value = "/get_cached_weight_logs", method = {RequestMethod.GET, RequestMethod.POST})
    public List<WeightLog> getCacheWeightLog(){
        String json = (String) redisTemplate.opsForValue().get("weightLogs");
        if(json == null || json.isEmpty()) return new ArrayList<>();
        Type type = new TypeToken<List<WeightLog>>(){}.getType();
        return gson.fromJson(json ,type);
    }

    @RequestMapping(value = "/clear_cache", method = {RequestMethod.GET, RequestMethod.POST})
    public String clearCache(){
        Boolean deleted = redisTemplate.delete("weightLogs");
        return deleted ? "Deleted" : "Key not found";
    }
}
