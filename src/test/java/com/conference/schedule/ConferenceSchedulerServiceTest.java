package com.conference.schedule;

import com.conference.schedule.exceptions.NumberInTitleException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
//import org.junit.Test;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConferenceSchedulerServiceTest {

    @Test
    void testScheduleTwoTracks() throws NumberInTitleException {
        ConferenceSchedulerService service = new ConferenceSchedulerService();

        List<String> input = Arrays.asList(
                "Writing Fast Tests Against Enterprise Rails 60min",
                "Overdoing it in Python 45min",
                "Lua for the Masses 30min",
                "Ruby Errors from Mismatched Gem Versions 45min",
                "Rails for Python Developers lightning",
                "Communicating Over Distance 60min",
                "Communicating Over Distance 60min",
                "Communicating Over Distance 60min",
                "Communicating Over Distance 60min",
                "Communicating Over Distance 60min",
                "Communicating Over Distance 60min"

        );

        List<Track> result = service.schedule(input);

        // Validate output to check if two tracks have been created
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(2,result.size());
        // validate if lunch has started at 12 for both tracks
        Assertions.assertEquals(LocalTime.of(12,0),result.get(0).getLunch());
        Assertions.assertEquals(LocalTime.of(12,0),result.get(1).getLunch());

        //validate if the second track which is having only three talks should not start before 4
        Assertions.assertEquals(3,result.get(1).getMorningSession().size());
        Assertions.assertNull(result.get(1).getAfternoonSession());
        Assertions.assertEquals(LocalTime.of(16,00),(result.get(1).getNetworkEventStart()));

    }

    @Test
    void testScheduleWithOneTalk() throws NumberInTitleException {
        ConferenceSchedulerService service = new ConferenceSchedulerService();

        List<String> input = Arrays.asList(
                "Writing Fast Tests Against Enterprise Rails 60min"
            );

        List<Track> result = service.schedule(input);

        assertEquals(1,result.size());
        Assertions.assertEquals(1,result.get(0).getMorningSession().size());
        Assertions.assertNull(result.get(0).getAfternoonSession());
        Assertions.assertEquals(LocalTime.of(16,00),(result.get(0).getNetworkEventStart()));

    }

    @Test
    void testNoGapBetween() throws NumberInTitleException {
        ConferenceSchedulerService service = new ConferenceSchedulerService();

        List<String> input = Arrays.asList(
                "Writing Fast Tests Against Enterprise Rails 60min",
                "Writing Fast Tests Against Enterprise Rails 60min"
        );

        List<Track> result = service.schedule(input);

        assertEquals(1,result.size());
        Assertions.assertEquals(2,result.get(0).getMorningSession().size());
        assertEquals(LocalTime.of(9,0),result.get(0).getMorningSession().get(0).getSchedule());
        assertEquals(LocalTime.of(10,0),result.get(0).getMorningSession().get(1).getSchedule());
        Assertions.assertNull(result.get(0).getAfternoonSession());
        Assertions.assertEquals(LocalTime.of(16,00),(result.get(0).getNetworkEventStart()));

    }

    @Test
    void testNoNumbersTitle(){
        ConferenceSchedulerService service = new ConferenceSchedulerService();

        List<String> input = Arrays.asList(
                "Writing Fast Tests Against Enterprise 9 Rails 60min" +
                        ""
        );

        NumberInTitleException exception = assertThrows(NumberInTitleException.class, () -> service.schedule(input));

        assertEquals("Title is not in correct format", exception.getMessage());

    }

    @Test
    void testDurationInMinutes() throws NumberInTitleException {
        ConferenceSchedulerService service = new ConferenceSchedulerService();

        List<String> input = Arrays.asList(
                "Writing Fast Tests Against Enterprise Rails 60min",
                "Writing Fast Tests Against Enterprise Rails 60min"
        );

        List<Track> result = service.schedule(input);
        assertEquals(60,result.get(0).getMorningSession().get(0).duration);
        assertEquals(60,result.get(0).getMorningSession().get(1).duration);

    }

    @Test
    void testLunchTime() throws NumberInTitleException {
        ConferenceSchedulerService service = new ConferenceSchedulerService();

        List<String> input = Arrays.asList(
                "Writing Fast Tests Against Enterprise Rails 60min",
                "Writing Fast Tests Against Enterprise Rails 60min",
                "Writing Fast Tests Against Enterprise Rails 70min",
                "Writing Fast Tests Against Enterprise Rails 60min",
                "Writing Fast Tests Against Enterprise Rails 60min",
                "Writing Fast Tests Against Enterprise Rails 70min"
        );
        List<Track> result = service.schedule(input);
        Assertions.assertEquals(LocalTime.of(12,0),result.get(0).getLunch());

        input = Arrays.asList(
                "Writing Fast Tests Against Enterprise Rails 60min",
                "Writing Fast Tests Against Enterprise Rails 50min"
        );
        result = service.schedule(input);
        Assertions.assertEquals(LocalTime.of(12,0),result.get(0).getLunch());
    }

    @Test
    void testNetworkScheduleBetween4and5() throws NumberInTitleException {
        ConferenceSchedulerService service = new ConferenceSchedulerService();

        List<String> input = Arrays.asList(
                "Writing Fast Tests Against Enterprise Rails 60min",
                "Overdoing it in Python 45min",
                "Lua for the Masses 30min",
                "Ruby Errors from Mismatched Gem Versions 45min",
                "Rails for Python Developers lightning",
                "Communicating Over Distance 60min",
                "Communicating Over Distance 60min",
                "Communicating Over Distance 60min",
                "Ruby Errors from Mismatched Gem Versions 45min"

        );

        List<Track> result = service.schedule(input);

        // Validate output to check if tracks have been created
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(1,result.size());
        // Validate if track1 Network has started at 16:50 because we are assigning 3 talks with 60 , one with 45 and one lightning in afternoon session
        Assertions.assertEquals(LocalTime.of(16,50),(result.get(0).getNetworkEventStart()));
    }
}
