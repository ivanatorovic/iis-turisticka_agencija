package com.example.turisticka_agencija.dto;

public class MonthlyArrangementSalesDto {

    private int month;
    private String monthName;
    private long reservationCount;
    private double revenue;

    public MonthlyArrangementSalesDto() {
    }

    public MonthlyArrangementSalesDto(int month, String monthName, long reservationCount, double revenue) {
        this.month = month;
        this.monthName = monthName;
        this.reservationCount = reservationCount;
        this.revenue = revenue;
    }

    public int getMonth() {
        return month;
    }

    public String getMonthName() {
        return monthName;
    }

    public long getReservationCount() {
        return reservationCount;
    }

    public double getRevenue() {
        return revenue;
    }
}