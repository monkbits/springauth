package com.example.dev.Controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/health")
public class HealthController {
    @Autowired
    public RedisTemplate redisTemplate;

    @Autowired
    public Gson gson;

    @RequestMapping(value = "/getWeightLogs", method = {RequestMethod.GET, RequestMethod.POST})
    public HashMap<String , String> getWeightLog(){
        HashMap<String, String> hm = new HashMap<>();
        hm.put("16-11-2025",String.valueOf(89.5));
        hm.put("30-08-2025",String.valueOf(95.0));
        redisTemplate.opsForValue().set("weightLogs",gson.toJson(hm));
        return hm;
    }
    @RequestMapping(value = "/getCacheWeightLogs", method = {RequestMethod.GET, RequestMethod.POST})
    public HashMap<String , String> getCacheWeightLog(){
        return gson.fromJson((String) redisTemplate.opsForValue().get("weightLogs"),HashMap.class);
    }

    @RequestMapping(value = "/clearCache", method = {RequestMethod.GET, RequestMethod.POST})
    public String clearCache(){
        Boolean deleted = redisTemplate.delete("weightLogs");
        return deleted ? "Deleted" : "Key not found";
    }
}
