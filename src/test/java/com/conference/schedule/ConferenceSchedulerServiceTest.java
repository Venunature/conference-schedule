package com.conference.schedule;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ConferenceSchedulerServiceTest {

    @Test
    void testSchedule() {
        ConferenceSchedulerService service = new ConferenceSchedulerService();

        List<String> input = Arrays.asList(
                "Writing Fast Tests Against Enterprise Rails 60min",
                "Overdoing it in Python 45min",
                "Lua for the Masses 30min",
                "Ruby Errors from Mismatched Gem Versions 45min",
                "Rails for Python Developers lightning",
                "Communicating Over Distance 60min",
                "Communicating Over Distance 60min",
                "Communicating Over Distance 60min"
        );

        List<String> result = service.schedule(input);

        // Validate output contains key elements
        assertTrue(result.contains("Track 1:"));
        assertTrue(result.contains("Networking Event: 16:05"));
        assertTrue(result.stream().anyMatch(s -> s.contains("Writing Fast Tests Against Enterprise Rails")));
    }

    @Test
    void testNetworkSchedule(){
        ConferenceSchedulerService service = new ConferenceSchedulerService();

        List<String> input = Arrays.asList(
                "Writing Fast Tests Against Enterprise Rails 60min"
            );

        List<String> result = service.schedule(input);

        assertTrue(result.contains("Networking Event: 16:00"));

        input = Arrays.asList(
                "Writing Fast Tests Against Enterprise Rails 60min",
                "Overdoing it in Python 45min",
                "Lua for the Masses 30min",
                "Ruby Errors from Mismatched Gem Versions 45min",
                "Rails for Python Developers lightning",
                "Communicating Over Distance 60min",
                "Communicating Over Distance 60min",
                "Communicating Over Distance 60min"
        );
        result = service.schedule(input);

        assertTrue(result.contains("Networking Event: 16:05"));
    }

    @Test
    void testNoGapBetween(){
        ConferenceSchedulerService service = new ConferenceSchedulerService();

        List<String> input = Arrays.asList(
                "Writing Fast Tests Against Enterprise Rails 60min",
                "Writing Fast Tests Against Enterprise Rails 60min"
        );

        List<String> result = service.schedule(input);

        assertTrue(result.contains("Networking Event: 16:00"));

    }

    @Test
    void testNoNumbersTitle(){
        ConferenceSchedulerService service = new ConferenceSchedulerService();

        List<String> input = Arrays.asList(
                "Writing Fast Tests Against Enterprise Rails 60min",
                "Writing Fast Tests Against Enterprise Rails 60min"
        );

        List<String> result = service.schedule(input);

        assertTrue(result.contains("Networking Event: 16:00"));
    }

    @Test
    void testDurationInMinutes(){
        ConferenceSchedulerService service = new ConferenceSchedulerService();

        List<String> input = Arrays.asList(
                "Writing Fast Tests Against Enterprise Rails 60min",
                "Writing Fast Tests Against Enterprise Rails 60min"
        );

        List<String> result = service.schedule(input);

        for (String s : result) {
            System.out.println(s);
        }

        assertTrue(result.contains("Networking Event: 16:00"));
    }

    @Test
    void testLunchTime(){
        ConferenceSchedulerService service = new ConferenceSchedulerService();

        List<String> input = Arrays.asList(
                "Writing Fast Tests Against Enterprise Rails 60min",
                "Writing Fast Tests Against Enterprise Rails 60min",
                "Writing Fast Tests Against Enterprise Rails 70min",
                "Writing Fast Tests Against Enterprise Rails 60min",
                "Writing Fast Tests Against Enterprise Rails 60min",
                "Writing Fast Tests Against Enterprise Rails 70min"
        );

        List<String> result = service.schedule(input);

        for (String s : result) {
            System.out.println(s);
        }
        assertTrue(result.contains("Track 1:"));
    }
}
