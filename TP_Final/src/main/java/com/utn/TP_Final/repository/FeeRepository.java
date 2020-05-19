package com.utn.TP_Final.repository;

import com.utn.TP_Final.model.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeeRepository extends JpaRepository<Fee, Integer> {

    //fijarse si funciona correctamente

    @Query(value = "select * from fees f join cities c on f.sourceCityId = c.idCity where c.cityName = ?1", nativeQuery = true)
    List<Fee> findBySourceCity(String city);

    @Query(value = "select * from fees f join cities c on f.destinationCityId = c.idCity where c.cityName = ?1", nativeQuery = true)
    List<Fee> findByDestinationCity(String city);

    @Query(value = "select * from fees f join cities c on f.destinationCityId = c.idCity or f.sourceCityid = c.idCity where c.prefixNumber = ?1", nativeQuery = true)
    Fee findByPrefix(Integer prefixNumber);
}
