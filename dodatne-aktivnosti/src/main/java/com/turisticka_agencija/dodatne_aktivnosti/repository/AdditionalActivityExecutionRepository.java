package com.turisticka_agencija.dodatne_aktivnosti.repository;

import com.turisticka_agencija.dodatne_aktivnosti.model.AdditionalActivityExecution;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdditionalActivityExecutionRepository extends Neo4jRepository<AdditionalActivityExecution, Long> {

    @Query("""
        MATCH (customer:Customer)-[:REGISTERED_FOR]->(:AdditionalActivityExecution)-[:EXECUTES_ACTIVITY]->(commonActivity:AdditionalActivity)
        MATCH (similarCustomer:Customer)-[:REGISTERED_FOR]->(:AdditionalActivityExecution)-[:EXECUTES_ACTIVITY]->(commonActivity)

        MATCH (similarCustomer)-[:REGISTERED_FOR]->(recommendedExecution:AdditionalActivityExecution)
              -[executesRel:EXECUTES_ACTIVITY]->(activity:AdditionalActivity)

        MATCH (recommendedExecution)-[partOfRel:PART_OF]->(arr:Arrangement)

        WHERE customer.customerId = $customerId
          AND similarCustomer.customerId <> customer.customerId
          AND (customer)-[:BOOKED]->(arr)
          AND NOT (customer)-[:REGISTERED_FOR]->(recommendedExecution)
          AND recommendedExecution.status = 'UPCOMING'
          AND recommendedExecution.capacity > recommendedExecution.reservedSpots

        WITH recommendedExecution,
             executesRel,
             activity,
             partOfRel,
             arr,
             COUNT(DISTINCT similarCustomer) AS similarityScore

        ORDER BY similarityScore DESC

        RETURN recommendedExecution,
               collect(executesRel),
               collect(activity),
               collect(partOfRel),
               collect(arr)
    """)
    List<AdditionalActivityExecution> recommendBySimilarCustomers(Long customerId);


    @Query("""
        MATCH (customer:Customer)-[:LIKES]->(category:Category)
        MATCH (activity:AdditionalActivity)-[:BELONGS_TO]->(category)

        MATCH (recommendedExecution:AdditionalActivityExecution)-[executesRel:EXECUTES_ACTIVITY]->(activity)
        MATCH (recommendedExecution)-[partOfRel:PART_OF]->(arr:Arrangement)

        WHERE customer.customerId = $customerId
          AND (customer)-[:BOOKED]->(arr)
          AND NOT (customer)-[:REGISTERED_FOR]->(recommendedExecution)
          AND recommendedExecution.status = 'UPCOMING'
          AND recommendedExecution.capacity > recommendedExecution.reservedSpots

        RETURN recommendedExecution,
               collect(executesRel),
               collect(activity),
               collect(partOfRel),
               collect(arr)
    """)
    List<AdditionalActivityExecution> recommendByCategory(Long customerId);


    @Query("""
        MATCH (customer:Customer)-[:BOOKED]->(arr:Arrangement)
        MATCH (existingExecution:AdditionalActivityExecution)-[:PART_OF]->(arr)

        MATCH (recommendedExecution:AdditionalActivityExecution)-[partOfRel:PART_OF]->(arr)
        MATCH (recommendedExecution)-[executesRel:EXECUTES_ACTIVITY]->(activity:AdditionalActivity)

        WHERE customer.customerId = $customerId
          AND NOT (customer)-[:REGISTERED_FOR]->(recommendedExecution)
          AND recommendedExecution.status = 'UPCOMING'
          AND recommendedExecution.capacity > recommendedExecution.reservedSpots

        WITH recommendedExecution,
             executesRel,
             activity,
             partOfRel,
             arr,
             AVG(existingExecution.price) AS avgPrice

        WHERE recommendedExecution.price < avgPrice

        ORDER BY recommendedExecution.price ASC

        RETURN recommendedExecution,
               collect(executesRel),
               collect(activity),
               collect(partOfRel),
               collect(arr)
    """)
    List<AdditionalActivityExecution> findAffordableForCustomer(Long customerId);


    @Query("""
        MATCH (customer:Customer)-[:BOOKED]->(arr:Arrangement)

        MATCH (recommendedExecution:AdditionalActivityExecution)-[partOfRel:PART_OF]->(arr)
        MATCH (recommendedExecution)-[executesRel:EXECUTES_ACTIVITY]->(activity:AdditionalActivity)

        WHERE customer.customerId = $customerId
          AND NOT (customer)-[:REGISTERED_FOR]->(recommendedExecution)
          AND recommendedExecution.status = 'UPCOMING'
          AND recommendedExecution.capacity > recommendedExecution.reservedSpots

        WITH recommendedExecution,
             executesRel,
             activity,
             partOfRel,
             arr,
             toFloat(recommendedExecution.reservedSpots) / recommendedExecution.capacity AS occupancyRate

        WHERE occupancyRate >= 0.7

        ORDER BY occupancyRate DESC, recommendedExecution.reservedSpots DESC

        RETURN recommendedExecution,
               collect(executesRel),
               collect(activity),
               collect(partOfRel),
               collect(arr)
    """)
    List<AdditionalActivityExecution> findPopularForCustomer(Long customerId);
}