package com.example.turisticka_agencija.projection;

public interface ActivityOccupancyProjection {
    String getActivityName();
    Integer getCapacity();
    Integer getReservedSpots();
    Double getOccupancyRate();
}
