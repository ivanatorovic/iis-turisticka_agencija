package com.turisticka_agencija.dodatne_aktivnosti.service;

import com.turisticka_agencija.dodatne_aktivnosti.dto.RegistrationRequest;
import com.turisticka_agencija.dodatne_aktivnosti.model.Arrangement;
import com.turisticka_agencija.dodatne_aktivnosti.model.Category;
import com.turisticka_agencija.dodatne_aktivnosti.model.Customer;
import com.turisticka_agencija.dodatne_aktivnosti.model.Registration;

import java.util.List;

public interface ICustomerService {

    List<Customer> findAll();

    Customer findById(Long id);

    Customer save(Customer customer);

    Customer update(Long id, Customer customer);

    void delete(Long id);

    Customer registerOrUpdateActivity(Long customerId, RegistrationRequest request);
    void deleteRegistrationForExecution(Long customerId, Long executionId);

    void addFavoriteCategory(Long customerId, Long categoryId);

    void removeFavoriteCategory(Long customerId, Long categoryId);

    Customer addBookedArrangement(Long customerId, Long arrangementId);

    Customer removeBookedArrangement(Long customerId, Long arrangementId);

    List<Category> findFavoriteCategoriesByCustomerId(Long customerId);

    List<Registration> findRegistrationsByCustomerId(Long customerId);

    List<Arrangement> findBookedArrangementsByCustomerId(Long customerId);
}