package com.lerkin.qa.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class FeignExceptionDecoder {

    @SneakyThrows
    public static String exceptionMessageParser(String exceptionText) {

        int firstIndexOf = exceptionText.indexOf('{');
        int lastIndexOf = exceptionText.lastIndexOf('}');
        String json = exceptionText.substring(firstIndexOf, lastIndexOf + 1);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        return jsonNode.get("message").asText();

    }
}
