package com.utn.TP_Final.repository;

import com.utn.TP_Final.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    @Query(value = "select * from persons where firstname = ?1", nativeQuery = true)
    List<Person> findByName(String name);

}
