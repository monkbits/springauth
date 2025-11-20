package com.example.dev.config.security;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public Gson gson(){
        return new Gson();
    }


}
