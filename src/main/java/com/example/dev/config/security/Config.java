package com.example.dev.config.security;

import com.google.gson.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class Config {
    @Bean
    public Gson gson() {
        return new GsonBuilder()
                // LocalDate serialize + deserialize
                .registerTypeAdapter(LocalDate.class,
                        (JsonSerializer<LocalDate>) (src, type, context) ->
                                new JsonPrimitive(src.toString()))
                .registerTypeAdapter(LocalDate.class,
                        (JsonDeserializer<LocalDate>) (json, type, context) ->
                                LocalDate.parse(json.getAsString()))

                // LocalDateTime serialize + deserialize
                .registerTypeAdapter(LocalDateTime.class,
                        (JsonSerializer<LocalDateTime>) (src, type, context) ->
                                new JsonPrimitive(src.toString()))
                .registerTypeAdapter(LocalDateTime.class,
                        (JsonDeserializer<LocalDateTime>) (json, type, context) ->
                                LocalDateTime.parse(json.getAsString()))

                .create();
    }



}
