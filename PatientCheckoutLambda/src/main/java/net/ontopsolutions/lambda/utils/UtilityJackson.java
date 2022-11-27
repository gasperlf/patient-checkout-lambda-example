package net.ontopsolutions.lambda.utils;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.IOException;

public class UtilityJackson {

    private static final ObjectMapper mapper = new ObjectMapper();
    @SneakyThrows
    public static <T> T parser(String body, Class<T> clazz)  {
        return mapper.readValue(body, clazz);
    }

    @SneakyThrows
    public static <T> T parser(S3ObjectInputStream body, Class<T> clazz) {
        return mapper.readValue(body, clazz);
    }

    @SneakyThrows
    public static String parserString(Object object) {
        return mapper.writeValueAsString(object);
    }

    @SneakyThrows
    public static <T> T parser(S3ObjectInputStream body, TypeReference<T> listTypeReference)  {
        return mapper.readValue(body, listTypeReference);
    }
}
