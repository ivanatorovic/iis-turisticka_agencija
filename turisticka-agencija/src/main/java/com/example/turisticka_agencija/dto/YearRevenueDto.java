package com.example.turisticka_agencija.dto;

public class YearRevenueDto {

    private int year;
    private long reservationCount;
    private double revenue;

    public YearRevenueDto() {
    }

    public YearRevenueDto(int year, long reservationCount, double revenue) {
        this.year = year;
        this.reservationCount = reservationCount;
        this.revenue = revenue;
    }

    public int getYear() {
        return year;
    }

    public long getReservationCount() {
        return reservationCount;
    }

    public double getRevenue() {
        return revenue;
    }
}