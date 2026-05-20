package com.Pro_Connect.ConnectionService.repository;

import com.Pro_Connect.ConnectionService.entity.Person;

import java.util.List;
import java.util.Optional;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends Neo4jRepository<Person,Long> {
       Optional<Person> findByUserId(Long userId);

//       Query to get the first degree connection
@Query("MATCH (personA:Person)-[:CONNECTED_TO]-(personB:Person) " +
        "WHERE personA.userId = $userId " +
        "RETURN personB")
    List<Person> getFirstDegreeConnections(Long userId);
}
