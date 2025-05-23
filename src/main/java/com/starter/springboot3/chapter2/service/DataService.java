package com.starter.springboot3.chapter2.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
public class DataService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public MyData loadDataFromJson() throws IOException {
        try (InputStream inputStream =
                getClass().getClassLoader().getResourceAsStream("config/my-data.json")) {
            if (inputStream == null) {
                throw new IOException("Cannot find resource config/my-data.json");
            }
            String jsonContent =
                    FileCopyUtils.copyToString(
                            new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            return objectMapper.readValue(jsonContent, MyData.class);
        }
    }

    public String convertDataToJson(MyData data) throws IOException {
        return objectMapper.writeValueAsString(data);
    }
}
