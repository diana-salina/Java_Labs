package ru.nsu.salina;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JParser<T> {
    private ObjectMapper mapper;
    public JParser() {
        mapper = new ObjectMapper();
    }
    public T read(String pattern, Class<T> data) throws JsonProcessingException {
        return mapper.readValue(pattern, data);
    }

    public String write(Object data) throws IOException {
        return mapper.writeValueAsString(data);
    }
}
