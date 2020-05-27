package com.utn.TP_Final.repository;

import com.utn.TP_Final.model.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeeRepository extends JpaRepository<Fee, Integer> {

    //fijarse si funciona correctamente

    @Query(value = "select * from fees f join cities c on f.source_city_id = c.id_city where c.city_name = ?1", nativeQuery = true)
    List<Fee> findBySourceCity(String city);

    @Query(value = "select * from fees f join cities c on f.destination_city_id = c.id_city where c.city_name = ?1", nativeQuery = true)
    List<Fee> findByDestinationCity(String city);

    @Query(value = "select * from fees f join cities c on f.destination_city_id = c.id_city or f.source_city_id = c.id_city where c.prefix_number = ?1", nativeQuery = true)
    Fee findByPrefix(Integer prefixNumber);
}
