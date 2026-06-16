package com.turisticka_agencija.dodatne_aktivnosti.repository;

import com.turisticka_agencija.dodatne_aktivnosti.model.AdditionalActivity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdditionalActivityRepository extends Neo4jRepository<AdditionalActivity, Long> {
    @Query("""
    MATCH (a:AdditionalActivity)
    WHERE a.activityId = $activityId
    MATCH (c:Category)
    WHERE c.categoryId = $categoryId
    MERGE (a)-[:BELONGS_TO]->(c)
    RETURN count(c)
""")
    Long addCategoryRelation(Long activityId, Long categoryId);


    @Query("""
    MATCH (a:AdditionalActivity)
    WHERE a.activityId = $activityId
    MATCH (c:Category)
    WHERE c.categoryId = $categoryId
    OPTIONAL MATCH (a)-[r:BELONGS_TO]->(c)
    DELETE r
    RETURN count(c)
""")
    Long removeCategoryRelation(Long activityId, Long categoryId);
}