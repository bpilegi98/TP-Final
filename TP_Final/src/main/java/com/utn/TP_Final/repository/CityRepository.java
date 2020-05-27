package com.utn.TP_Final.repository;

import com.utn.TP_Final.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    @Query(value = "select * from cities where city_name = ?1", nativeQuery = true)
    List<City> findByName(String name);

    @Query(value = "select * from cities where prefix_number = ?1", nativeQuery = true)
    City findByPrefix(Integer prefix);
}
