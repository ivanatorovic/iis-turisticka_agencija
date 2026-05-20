package com.example.turisticka_agencija.controller;
import com.example.turisticka_agencija.model.Destination;
import com.example.turisticka_agencija.repository.DestinationRepository;
import org.springframework.web.bind.annotation.*;


import java.util.List;

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


    @PutMapping("/{id}")
    public Destination updateDestination(@PathVariable Long id, @RequestBody Destination updatedDestination) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destinacija nije pronađena"));

        destination.setCategory(updatedDestination.getCategory());
        destination.setCountry(updatedDestination.getCountry());
        destination.setName(updatedDestination.getName());
        destination.setDescription(updatedDestination.getDescription());

        return destinationRepository.save(destination);
    }


}