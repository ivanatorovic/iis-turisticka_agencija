package com.example.turisticka_agencija.dto;

public class ActivityPopularityDto {
    private String activityName;
    private long registrationsCount;
    private long participantsCount;

    public ActivityPopularityDto(String activityName, long registrationsCount, long participantsCount) {
        this.activityName = activityName;
        this.registrationsCount = registrationsCount;
        this.participantsCount = participantsCount;
    }

    public String getActivityName() { return activityName; }
    public long getRegistrationsCount() { return registrationsCount; }
    public long getParticipantsCount() { return participantsCount; }
}