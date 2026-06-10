package com.turisticka_agencija.dodatne_aktivnosti.repository;

import com.turisticka_agencija.dodatne_aktivnosti.model.AdditionalActivity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdditionalActivityRepository extends Neo4jRepository<AdditionalActivity, Long> {
}