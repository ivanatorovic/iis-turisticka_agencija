package com.turisticka_agencija.dodatne_aktivnosti.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node("Customer")
public class Customer {

    @Id
    private Long customerId;

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String contact;

    @JsonIgnore
    @Relationship(type = "LIKES")
    private Set<Category> favoriteCategories = new HashSet<>();

    @JsonIgnore
    @Relationship(type = "REGISTERED_FOR")
    private Set<Registration> registrations = new HashSet<>();

    @JsonIgnore
    @Relationship(type = "BOOKED")
    private Set<Arrangement> bookedArrangements = new HashSet<>();

    public Customer() {
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Set<Category> getFavoriteCategories() {
        return favoriteCategories;
    }

    public void setFavoriteCategories(Set<Category> favoriteCategories) {
        this.favoriteCategories = favoriteCategories;
    }

    public Set<Registration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(Set<Registration> registrations) {
        this.registrations = registrations;
    }

    public Set<Arrangement> getBookedArrangements() {
        return bookedArrangements;
    }

    public void setBookedArrangements(Set<Arrangement> bookedArrangements) {
        this.bookedArrangements = bookedArrangements;
    }
}