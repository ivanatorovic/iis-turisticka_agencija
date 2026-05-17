package com.example.turisticka_agencija.dto;

public class PopularDestinationDto {

    private String destinationName;
    private String country;
    private long reservationCount;
    private double revenue;

    public PopularDestinationDto() {
    }

    public PopularDestinationDto(String destinationName, String country, long reservationCount, double revenue) {
        this.destinationName = destinationName;
        this.country = country;
        this.reservationCount = reservationCount;
        this.revenue = revenue;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public String getCountry() {
        return country;
    }

    public long getReservationCount() {
        return reservationCount;
    }

    public double getRevenue() {
        return revenue;
    }
}