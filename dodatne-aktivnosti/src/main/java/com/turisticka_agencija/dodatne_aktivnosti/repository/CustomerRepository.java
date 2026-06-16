package com.turisticka_agencija.dodatne_aktivnosti.repository;

import com.turisticka_agencija.dodatne_aktivnosti.model.Customer;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CustomerRepository extends Neo4jRepository<Customer, Long> {

    @Query("""
        MATCH (c:Customer)
        WHERE c.customerId = $customerId
        MATCH (e:AdditionalActivityExecution)
        WHERE e.executionId = $executionId
        MERGE (c)-[r:REGISTERED_FOR]->(e)
        ON CREATE SET r.registrationId = $registrationId,
                      r.registrationDate = $registrationDate,
                      r.numberOfPeople = $numberOfPeople,
                      r.status = $status
        ON MATCH SET r.registrationId = $registrationId,
                     r.registrationDate = $registrationDate,
                     r.numberOfPeople = $numberOfPeople,
                     r.status = $status
        RETURN c
    """)
    Customer registerOrUpdateActivity(@Param("customerId") Long customerId,
                                      @Param("executionId") Long executionId,
                                      @Param("registrationId") Long registrationId,
                                      @Param("registrationDate") LocalDateTime registrationDate,
                                      @Param("numberOfPeople") Integer numberOfPeople,
                                      @Param("status") String status);


    @Query("""
        MATCH (c:Customer)-[r:REGISTERED_FOR]->(e:AdditionalActivityExecution)
        WHERE c.customerId = $customerId
          AND e.executionId = $executionId
        DELETE r
    """)
    void deleteRegistrationForExecution(@Param("customerId") Long customerId,
                                        @Param("executionId") Long executionId);


    @Query("""
        MATCH (c:Customer)-[r:REGISTERED_FOR]->(e:AdditionalActivityExecution)
        WHERE c.customerId = $customerId
          AND r.registrationId = $registrationId
        SET r.status = $status
        RETURN c
    """)
    Customer updateRegistrationStatus(@Param("customerId") Long customerId,
                                      @Param("registrationId") Long registrationId,
                                      @Param("status") String status);

    @Query("""
    MATCH (c:Customer)
    WHERE c.customerId = $customerId
    MATCH (cat:Category)
    WHERE cat.categoryId = $categoryId
    MERGE (c)-[:LIKES]->(cat)
    RETURN count(cat)
""")
    Long addFavoriteCategoryRelation(@Param("customerId") Long customerId,
                                     @Param("categoryId") Long categoryId);


    @Query("""
    MATCH (c:Customer)
    WHERE c.customerId = $customerId
    MATCH (cat:Category)
    WHERE cat.categoryId = $categoryId
    OPTIONAL MATCH (c)-[r:LIKES]->(cat)
    DELETE r
    RETURN count(cat)
""")
    Long removeFavoriteCategoryRelation(@Param("customerId") Long customerId,
                                        @Param("categoryId") Long categoryId);
}