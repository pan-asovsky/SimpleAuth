package dev.panasovsky.module.auth.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class JsonConvertHelper {

    public static JsonNode getJsonResponse(final String status, final String text) {

        final String jsonString = assembleResponseToJsonString(status, text);
        return JsonConvertHelper.stringToJson(jsonString);
    }

    private static String assembleResponseToJsonString(final String status, final String text) {
        return "{\"status\":\"" + status + "\", \"message\":\"" + text + "\"}";
    }

    private static JsonNode stringToJson(final String jsonString) {

        final ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(jsonString);
        } catch (final JsonProcessingException e) {
            log.error("can't parse String to JsonNode: {}", e.getLocalizedMessage());
        }

        return jsonNode;
    }

}