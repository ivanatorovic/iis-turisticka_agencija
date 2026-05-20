package com.example.turisticka_agencija.controller;
import com.example.turisticka_agencija.model.Destination;
import com.example.turisticka_agencija.repository.DestinationRepository;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/destinations")
public class DestinationController {

    private final DestinationRepository destinationRepository;

    public DestinationController(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    @GetMapping
    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    @PostMapping
    public Destination createDestination(@RequestBody Destination destination) {
        return destinationRepository.save(destination);
    }

    @DeleteMapping("/{id}")
    public void deleteDestination(@PathVariable Long id) {
        destinationRepository.deleteById(id);
    }

    @GetMapping("/category/{category}")
    public List<Destination> getByCategory(@PathVariable String category) {
        return destinationRepository.findByCategory(category);
    }

    @GetMapping("/country/{country}")
    public List<Destination> getByCountry(@PathVariable String country) {
        return destinationRepository.findByCountry(country);
    }


    @PatchMapping("/{id}")
    public Destination patchDestination(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates
    ) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destinacija nije pronađena"));

        if (updates.containsKey("category")) {
            destination.setCategory((String) updates.get("category"));
        }

        if (updates.containsKey("country")) {
            destination.setCountry((String) updates.get("country"));
        }

        if (updates.containsKey("name")) {
            destination.setName((String) updates.get("name"));
        }

        if (updates.containsKey("description")) {
            destination.setDescription((String) updates.get("description"));
        }

        return destinationRepository.save(destination);
    }


}