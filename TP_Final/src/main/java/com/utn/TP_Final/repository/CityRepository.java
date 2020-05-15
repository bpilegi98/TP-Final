package com.utn.TP_Final.repository;

import com.utn.TP_Final.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    @Query(value = "select * from cities where cityName = ?1", nativeQuery = true)
    List<City> findByName(String name);
}
