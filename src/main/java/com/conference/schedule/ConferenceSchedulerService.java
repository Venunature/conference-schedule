package com.conference.schedule;

import org.joda.time.DateTime;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ConferenceSchedulerService {

    static final int MORNING_SESSION_MAX = 180;
    static final int AFTERNOON_SESSION_MIN = 180;
    static final int AFTERNOON_SESSION_MAX = 240;

    public List<String> schedule(List<String> input) {
        List<Talk> talks = parseInput(input);
        List<Track> tracks = createTracks(talks);
        return formatOutput(tracks);
    }

    private List<Talk> parseInput(List<String> input) {
        List<Talk> talks = new ArrayList<>();
        for (String line : input) {
            int duration;
            if (line.endsWith("lightning")) {
                duration = 5;
            } else {
                duration = Integer.parseInt(line.replaceAll("\\D+", ""));
            }
            talks.add(new Talk(line, duration));
        }
        return talks;
    }

    private List<Track> createTracks(List<Talk> talks) {
        List<Track> tracks = new ArrayList<>();
        while (!talks.isEmpty()) {
            Track track = new Track();
            track.setMorningSession(fillSession(talks, MORNING_SESSION_MAX, LocalTime.of(9,0,0)));
            if(!talks.isEmpty()) {
                track.setAfterNoonSession(fillSession(talks, AFTERNOON_SESSION_MAX,LocalTime.of(13,0,0)));
            }
            if(null !=track.getAfternoonSession() && !track.getAfternoonSession().isEmpty()){
                Talk lastTalk=track.getAfternoonSession().get(track.getAfternoonSession().size()-1);
                if(lastTalk.getSchedule().plusMinutes(lastTalk.duration).isAfter(LocalTime.of(16,0))){
                    track.setNetworkEventStart(lastTalk.schedule.plusMinutes(lastTalk.duration));
                }
            }
            if(track.getNetworkEventStart()==null){
                track.setNetworkEventStart(LocalTime.of(16,0));
            }
            tracks.add(track);
        }
        return tracks;
    }

    private List<Talk> fillSession(List<Talk> talks, int maxDuration, LocalTime startTime) {
        List<Talk> session = new ArrayList<>();
        int totalTime = 0;
        LocalTime currentTime= startTime;
        for (int i = 0; i < talks.size(); i++) {
            Talk talk= talks.get(i);
            if (totalTime + talk.duration <= maxDuration) {
                totalTime += talk.duration;
                talk.setSchedule((currentTime));
                session.add(talks.get(i));
                currentTime=currentTime.plusMinutes(talk.duration);
                talks.remove(i--);
            }
        }
        return session;
    }

    private List<String> formatOutput(List<Track> tracks) {
        List<String> output = new ArrayList<>();
        LocalTime morningStart = LocalTime.of(9, 0);
        LocalTime afternoonStart = LocalTime.of(13, 0);
        for (int i = 0; i < tracks.size(); i++) {
            Track track = tracks.get(i);
            output.add("Track " + (i + 1) + ":");
            output.addAll(formatSession(track.getMorningSession(), morningStart));
            output.add(track.getLunch()+" 60 mins");
            output.addAll(formatSession(track.getAfternoonSession(), afternoonStart));
            output.add("Networking Event: "+track.getNetworkEventStart());
        }
        return output;
    }

    private List<String> formatSession(List<Talk> session, LocalTime startTime) {
        List<String> output = new ArrayList<>();
        LocalTime currentTime = startTime;
        if(session!=null){
            for (Talk talk : session) {
                output.add(currentTime + " - " + currentTime.plusMinutes(talk.duration) + ": " + talk.title);
                currentTime = currentTime.plusMinutes(talk.duration);
            }
        }
        return output;
    }

}
