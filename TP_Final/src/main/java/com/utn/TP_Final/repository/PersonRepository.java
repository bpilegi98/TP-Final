package com.utn.TP_Final.repository;

import com.utn.TP_Final.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    @Query(value = "select * from persons where lastname = ?1", nativeQuery = true)
    List<Person> findByLastname(String name);

    @Query(value = "select * from persons where dni = ?1", nativeQuery = true)
    Person findByDni(Integer dni);

    @Query(value = "select * from persons p join telephonelines t on p.id_person = t.id_person where line_number = ?1", nativeQuery = true)
    Person findByLineNumber(String lineNumber);

    @Query(value = "select * from persons where username = ?1 and password = ?2", nativeQuery = true)
    Person findByUsername(String username, String password);

    @Query(value = "remove from persons where dni = ?1", nativeQuery = true)
    void delete(Integer dni);

}
