package com.conference.schedule;

import java.time.LocalTime;

public class Talk {
    String title;
    int duration;
    LocalTime schedule;

    Talk(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }

    public LocalTime getSchedule() {
        return schedule;
    }

    public void setSchedule(LocalTime schedule) {
        this.schedule = schedule;
    }


}