package com.turisticka_agencija.dodatne_aktivnosti.dto;

public class ArrangementDTO {

    private Long id;
    private String name;

    public ArrangementDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
}