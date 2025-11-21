package com.example.dev;

import com.example.dev.Controller.HealthController;
import com.example.dev.Entities.WeightLog;
import com.example.dev.config.security.JwtUtil;
import com.example.dev.service.CustomUserDetailsService;
import com.example.dev.service.WeightLogService;
import com.google.gson.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

//@SpringBootTest
//@AutoConfigureMockMvc
//@ContextConfiguration(classes = {HealthController.class, HealthControllerTest.class})
@WebMvcTest(HealthController.class)
@AutoConfigureMockMvc(addFilters = false) // disable Spring Security filters
public class HealthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeightLogService weightLogService;

    @MockBean
    private JwtUtil jwtUtil;   // REQUIRED

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private RedisTemplate<?, ?> redisTemplate;

    @MockBean
    private ValueOperations<String, Object> valueOperations;

    @BeforeEach
    void setup() {

//        JwtUtil jwtUtil = Mockito.mock(JwtUtil.class);
        ValueOperations valueOps = Mockito.mock(ValueOperations.class);
        Mockito.when(redisTemplate.opsForValue()).thenReturn(valueOps);
    }
    @TestConfiguration
    static class GsonTestConfig {

        @Bean
        public Gson gson() {
            return new GsonBuilder()
                    // LocalDate
                    .registerTypeAdapter(LocalDate.class,
                            (JsonSerializer<LocalDate>) (src, type, ctx) ->
                                    new JsonPrimitive(src.toString()))
                    .registerTypeAdapter(LocalDate.class,
                            (JsonDeserializer<LocalDate>) (json, type, ctx) ->
                                    LocalDate.parse(json.getAsString()))

                    // LocalDateTime (THIS FIXES YOUR ERROR)
                    .registerTypeAdapter(LocalDateTime.class,
                            (JsonSerializer<LocalDateTime>) (src, type, ctx) ->
                                    new JsonPrimitive(src.toString()))
                    .registerTypeAdapter(LocalDateTime.class,
                            (JsonDeserializer<LocalDateTime>) (json, type, ctx) ->
                                    LocalDateTime.parse(json.getAsString()))

                    .create();
        }

        @Bean
        public GsonHttpMessageConverter gsonHttpMessageConverter(Gson gson) {
            return new GsonHttpMessageConverter(gson);
        }
    }


    @Test
    void testGetWeightLogs() throws Exception {
        List<WeightLog> mockLogs = List.of(
                new WeightLog(null, LocalDate.of(2025, 11, 20), 90.00f)
        );

        Mockito.when(weightLogService.getWeightLogs())
                .thenReturn(mockLogs);

        mockMvc.perform(get("/health/get_weight_logs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].weight").value(90.0));
    }
}
