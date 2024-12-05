package com.conference.schedule;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Track {

    private LocalTime Lunch= LocalTime.of(12,0,0);
    private LocalTime networkEventStart;
    private List<Talk> morningSession;
    private List<Talk> afternoonSession;

    public void setMorningSession(List<Talk> talks){
        this.morningSession=talks;
    }

    public void setAfterNoonSession(List<Talk> talks){
        this.afternoonSession=talks;
    }

    public List<Talk> getMorningSession() {
        return morningSession;
    }

    public List<Talk> getAfternoonSession() {
        return afternoonSession;
    }

    public void setNetworkEventStart(LocalTime networkEventStart) {
        this.networkEventStart = networkEventStart;
    }

    public LocalTime getNetworkEventStart(){
        return this.networkEventStart;
    }

    public LocalTime getLunch(){
        return this.Lunch;
    }
}
