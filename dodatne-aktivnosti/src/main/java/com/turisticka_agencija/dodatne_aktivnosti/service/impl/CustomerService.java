package com.turisticka_agencija.dodatne_aktivnosti.service.impl;

import com.turisticka_agencija.dodatne_aktivnosti.dto.RegistrationRequest;
import com.turisticka_agencija.dodatne_aktivnosti.model.Arrangement;
import com.turisticka_agencija.dodatne_aktivnosti.model.Category;
import com.turisticka_agencija.dodatne_aktivnosti.model.Customer;
import com.turisticka_agencija.dodatne_aktivnosti.model.Registration;
import com.turisticka_agencija.dodatne_aktivnosti.repository.ArrangementRepository;
import com.turisticka_agencija.dodatne_aktivnosti.repository.CategoryRepository;
import com.turisticka_agencija.dodatne_aktivnosti.repository.CustomerRepository;
import com.turisticka_agencija.dodatne_aktivnosti.service.ICustomerService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final CategoryRepository categoryRepository;
    private final ArrangementRepository arrangementRepository;

    public CustomerService(
            CustomerRepository customerRepository,
            CategoryRepository categoryRepository,
            ArrangementRepository arrangementRepository
    ) {
        this.customerRepository = customerRepository;
        this.categoryRepository = categoryRepository;
        this.arrangementRepository = arrangementRepository;
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer update(Long id, Customer customer) {
        Customer existingCustomer = findById(id);

        if (customer.getFirstName() != null) {
            existingCustomer.setFirstName(customer.getFirstName());
        }

        if (customer.getLastName() != null) {
            existingCustomer.setLastName(customer.getLastName());
        }

        if (customer.getUsername() != null) {
            existingCustomer.setUsername(customer.getUsername());
        }

        if (customer.getEmail() != null) {
            existingCustomer.setEmail(customer.getEmail());
        }

        if (customer.getContact() != null) {
            existingCustomer.setContact(customer.getContact());
        }

        if (customer.getFavoriteCategories() != null && !customer.getFavoriteCategories().isEmpty()) {
            existingCustomer.setFavoriteCategories(customer.getFavoriteCategories());
        }

        if (customer.getBookedArrangements() != null && !customer.getBookedArrangements().isEmpty()) {
            existingCustomer.setBookedArrangements(customer.getBookedArrangements());
        }

        return customerRepository.save(existingCustomer);
    }

    @Override
    public void delete(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public Customer registerOrUpdateActivity(Long customerId, RegistrationRequest request) {
        return customerRepository.registerOrUpdateActivity(
                customerId,
                request.getExecutionId(),
                request.getRegistrationId(),
                LocalDateTime.now(),
                request.getNumberOfPeople(),
                request.getStatus()
        );
    }

    @Override
    public void deleteRegistrationForExecution(Long customerId, Long executionId) {
        customerRepository.deleteRegistrationForExecution(customerId, executionId);
    }

    @Override
    public void addFavoriteCategory(Long customerId, Long categoryId) {
        Long matchedCategories = customerRepository.addFavoriteCategoryRelation(
                customerId,
                categoryId
        );

        if (matchedCategories == null || matchedCategories == 0) {
            throw new RuntimeException(
                    "Customer or category not found. customerId: "
                            + customerId + ", categoryId: " + categoryId
            );
        }

    }

    @Override
    public void removeFavoriteCategory(Long customerId, Long categoryId) {
        Long matchedCategories = customerRepository.removeFavoriteCategoryRelation(
                customerId,
                categoryId
        );

        if (matchedCategories == null || matchedCategories == 0) {
            throw new RuntimeException(
                    "Customer or category not found. customerId: "
                            + customerId + ", categoryId: " + categoryId
            );
        }

    }

    @Override
    public Customer addBookedArrangement(Long customerId, Long arrangementId) {
        Customer customer = findById(customerId);

        Arrangement arrangement = arrangementRepository.findById(arrangementId)
                .orElseThrow(() -> new RuntimeException("Arrangement not found with id: " + arrangementId));

        customer.getBookedArrangements().add(arrangement);

        return customerRepository.save(customer);
    }

    @Override
    public Customer removeBookedArrangement(Long customerId, Long arrangementId) {
        Customer customer = findById(customerId);

        boolean removed = customer.getBookedArrangements().removeIf(
                arrangement -> arrangement.getArrangementId() != null
                        && arrangement.getArrangementId().equals(arrangementId)
        );

        if (!removed) {
            throw new RuntimeException("Booked arrangement not found for customer. Arrangement id: " + arrangementId);
        }

        return customerRepository.save(customer);
    }

    @Override
    public List<Category> findFavoriteCategoriesByCustomerId(Long customerId) {
        Customer customer = findById(customerId);
        return new ArrayList<>(customer.getFavoriteCategories());
    }

    @Override
    public List<Registration> findRegistrationsByCustomerId(Long customerId) {
        Customer customer = findById(customerId);
        return new ArrayList<>(customer.getRegistrations());
    }

    @Override
    public List<Arrangement> findBookedArrangementsByCustomerId(Long customerId) {
        Customer customer = findById(customerId);
        return new ArrayList<>(customer.getBookedArrangements());
    }
}