package com.example.dev.service;

import com.example.dev.Entities.WeightLog;
import com.example.dev.repository.WeightLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeightLogService {
    @Autowired
    public WeightLogRepository weightLogRepository;

    public boolean addWeightLog(WeightLog weightLog){
        try {
            weightLogRepository.save(weightLog);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public List<WeightLog> getWeightLogs() {
        return weightLogRepository.findAll();
    }
}
