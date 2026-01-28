package com.pragma.challenge.msvc_users.infrastructure.configuration.feign;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.QueryMapEncoder;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class FeignQueryBuilder implements QueryMapEncoder {
    @Override
    public Map<String, Object> encode(Object o) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(o, new TypeReference<>() {
        });
    }
}
