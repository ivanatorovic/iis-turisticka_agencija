package com.example.turisticka_agencija.controller;

import com.example.turisticka_agencija.model.CalendarStatus;
import com.example.turisticka_agencija.model.DestinationCalendar;
import com.example.turisticka_agencija.model.SeasonType;
import com.example.turisticka_agencija.repository.DestinationCalendarRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/destination-calendars")
public class DestinationCalendarController {

    private final DestinationCalendarRepository destinationCalendarRepository;

    public DestinationCalendarController(DestinationCalendarRepository destinationCalendarRepository) {
        this.destinationCalendarRepository = destinationCalendarRepository;
    }

    @GetMapping
    public List<DestinationCalendar> getAllCalendars() {
        return destinationCalendarRepository.findAll();
    }

    @GetMapping("/destination/{destinationId}")
    public List<DestinationCalendar> getCalendarsByDestination(@PathVariable Long destinationId) {
        return destinationCalendarRepository.findByDestinationId(destinationId);
    }

    @PostMapping
    public DestinationCalendar createCalendar(@RequestBody DestinationCalendar calendar) {
        if (calendar.getStatus() == null) {
            calendar.setStatus(com.example.turisticka_agencija.model.CalendarStatus.ACTIVE);
        }

        return destinationCalendarRepository.save(calendar);
    }

    @DeleteMapping("/{id}")
    public void deleteCalendar(@PathVariable Long id) {
        destinationCalendarRepository.deleteById(id);
    }

    @PatchMapping("/{id}")
    public DestinationCalendar patchCalendar(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates
    ) {
        DestinationCalendar calendar = destinationCalendarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Calendar not found"));

        if (updates.containsKey("name")) {
            calendar.setName((String) updates.get("name"));
        }

        if (updates.containsKey("startDate")) {
            calendar.setStartDate(
                    LocalDate.parse((String) updates.get("startDate"))
            );
        }

        if (updates.containsKey("endDate")) {
            calendar.setEndDate(
                    LocalDate.parse((String) updates.get("endDate"))
            );
        }

        if (updates.containsKey("seasonType")) {
            calendar.setSeasonType(
                    SeasonType.valueOf((String) updates.get("seasonType"))
            );
        }

        if (updates.containsKey("status")) {
            calendar.setStatus(
                    CalendarStatus.valueOf((String) updates.get("status"))
            );
        }

        return destinationCalendarRepository.save(calendar);
    }
}