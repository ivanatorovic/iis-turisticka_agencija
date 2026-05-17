package com.example.turisticka_agencija.dto;

public class PopularArrangementDto {

    private Long arrangementId;
    private String arrangementName;
    private long reservationCount;
    private double revenue;

    public PopularArrangementDto() {
    }

    public PopularArrangementDto(Long arrangementId, String arrangementName, long reservationCount, double revenue) {
        this.arrangementId = arrangementId;
        this.arrangementName = arrangementName;
        this.reservationCount = reservationCount;
        this.revenue = revenue;
    }

    public Long getArrangementId() {
        return arrangementId;
    }

    public String getArrangementName() {
        return arrangementName;
    }

    public long getReservationCount() {
        return reservationCount;
    }

    public double getRevenue() {
        return revenue;
    }
}