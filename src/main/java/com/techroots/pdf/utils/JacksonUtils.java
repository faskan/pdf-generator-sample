package com.techroots.pdf.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

public class JacksonUtils {

    public  static <T> T readJson(String json, Class<T> type) throws IOException {
        ObjectMapper objectMapper = defaultObjectMapper();
        return objectMapper.readerFor(type).readValue(json);
    }

    private static ObjectMapper defaultObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    public static String toJsonString(Object object) throws JsonProcessingException {
        return defaultObjectMapper().writeValueAsString(object);
    }
}
