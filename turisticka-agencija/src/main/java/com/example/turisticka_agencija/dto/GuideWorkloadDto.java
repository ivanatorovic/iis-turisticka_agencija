package com.example.turisticka_agencija.dto;

public class GuideWorkloadDto {
    private String guideName;
    private long activitiesCount;
    private long participantsCount;

    public GuideWorkloadDto(String guideName, long activitiesCount, long participantsCount) {
        this.guideName = guideName;
        this.activitiesCount = activitiesCount;
        this.participantsCount = participantsCount;
    }

    public String getGuideName() { return guideName; }
    public long getActivitiesCount() { return activitiesCount; }
    public long getParticipantsCount() { return participantsCount; }
}