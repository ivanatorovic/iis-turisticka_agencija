package com.turisticka_agencija.dodatne_aktivnosti.controller;

import com.turisticka_agencija.dodatne_aktivnosti.dto.RegistrationRequest;
import com.turisticka_agencija.dodatne_aktivnosti.model.Arrangement;
import com.turisticka_agencija.dodatne_aktivnosti.model.Category;
import com.turisticka_agencija.dodatne_aktivnosti.model.Customer;
import com.turisticka_agencija.dodatne_aktivnosti.model.Registration;
import com.turisticka_agencija.dodatne_aktivnosti.service.ICustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> findAll() {
        return new ResponseEntity<>(customerService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> findById(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Customer> save(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.save(customer), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable Long id,
                                           @RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.update(id, customer), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{customerId}/registrations")
    public ResponseEntity<Customer> registerOrUpdateActivity(@PathVariable Long customerId,
                                                             @RequestBody RegistrationRequest request) {
        return new ResponseEntity<>(
                customerService.registerOrUpdateActivity(customerId, request),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{customerId}/registrations/{executionId}")
    public ResponseEntity<Void> deleteRegistrationForExecution(@PathVariable Long customerId,
                                                               @PathVariable Long executionId) {
        customerService.deleteRegistrationForExecution(customerId, executionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{customerId}/favorite-categories/{categoryId}")
    public ResponseEntity<Customer> addFavoriteCategory(@PathVariable Long customerId,
                                                        @PathVariable Long categoryId) {
        return new ResponseEntity<>(
                customerService.addFavoriteCategory(customerId, categoryId),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{customerId}/favorite-categories/{categoryId}")
    public ResponseEntity<Customer> removeFavoriteCategory(@PathVariable Long customerId,
                                                           @PathVariable Long categoryId) {
        return new ResponseEntity<>(
                customerService.removeFavoriteCategory(customerId, categoryId),
                HttpStatus.OK
        );
    }

    @PostMapping("/{customerId}/booked-arrangements/{arrangementId}")
    public ResponseEntity<Customer> addBookedArrangement(@PathVariable Long customerId,
                                                         @PathVariable Long arrangementId) {
        return new ResponseEntity<>(
                customerService.addBookedArrangement(customerId, arrangementId),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{customerId}/booked-arrangements/{arrangementId}")
    public ResponseEntity<Customer> removeBookedArrangement(@PathVariable Long customerId,
                                                            @PathVariable Long arrangementId) {
        return new ResponseEntity<>(
                customerService.removeBookedArrangement(customerId, arrangementId),
                HttpStatus.OK
        );
    }

    @GetMapping("/{customerId}/favorite-categories")
    public ResponseEntity<List<Category>> findFavoriteCategoriesByCustomerId(@PathVariable Long customerId) {
        return new ResponseEntity<>(
                customerService.findFavoriteCategoriesByCustomerId(customerId),
                HttpStatus.OK
        );
    }

    @GetMapping("/{customerId}/registrations")
    public ResponseEntity<List<Registration>> findRegistrationsByCustomerId(@PathVariable Long customerId) {
        return new ResponseEntity<>(
                customerService.findRegistrationsByCustomerId(customerId),
                HttpStatus.OK
        );
    }

    @GetMapping("/{customerId}/booked-arrangements")
    public ResponseEntity<List<Arrangement>> findBookedArrangementsByCustomerId(@PathVariable Long customerId) {
        return new ResponseEntity<>(
                customerService.findBookedArrangementsByCustomerId(customerId),
                HttpStatus.OK
        );
    }
}