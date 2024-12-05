package com.conference.schedule;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class ConferenceSchedulerHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();

        try {
         String body = event.getBody();
            List<String> inputList = objectMapper.readValue(body, new TypeReference<List<String>>() {});
            String result = "Processed " + inputList.size() + " items: " + String.join(", ", inputList);
            response.setStatusCode(200);
            response.setBody("{\"message\": \"" + result + "\"}");

        } catch (Exception e) {
            context.getLogger().log("Error processing input: " + e.getMessage());
            response.setStatusCode(500);
            response.setBody("{\"error\": \"Unable to parse the input\"}");
        }

        return response;
    }

    public Optional<List<String>> getTalksForDate(String date){
        // read the file from s3 or any other data source
        return Optional.of(new ArrayList<>());
    }
}