package com.utn.TP_Final.repository;


import com.utn.TP_Final.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

    @Query(value = "select * from countries where countryName = ?1", nativeQuery = true) //fijarse si funciona
    List<Country> findByName(String name);
}
