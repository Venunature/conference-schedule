package com.conference.schedule;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.*;

public class ConferenceSchedulerHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());
    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();

        try {
         String body = event.getBody();
            System.out.println(body);
            List<String> inputList = objectMapper.readValue(body, new TypeReference<List<String>>() {});
            ConferenceSchedulerService service= new ConferenceSchedulerService();
            List<Track> tracks=service.schedule(inputList);
            String result = objectMapper.writeValueAsString(tracks);
            response.setStatusCode(200);
            response.setBody("{\"body\": \"" + result + "\"}");

        } catch (Exception e) {
            context.getLogger().log("Error processing input: " + e.getMessage());
            response.setStatusCode(500);
            response.setBody("{\"error\": \"Unable to parse the input\"}");
        }

        return response;
    }
}
