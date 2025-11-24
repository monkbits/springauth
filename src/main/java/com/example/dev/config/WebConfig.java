package com.example.dev.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        // Serve files from 'uploads' folder relative to project root
        registry.addResourceHandler("/files/**")
                .addResourceLocations("file:uploads/");
    }
}