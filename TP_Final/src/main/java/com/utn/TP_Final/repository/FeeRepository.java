package com.utn.TP_Final.repository;

import com.utn.TP_Final.model.Fee;
import com.utn.TP_Final.projections.FeeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Repository
public interface FeeRepository extends JpaRepository<Fee, Integer> {

    //fijarse si funciona correctamente

    @Query(value = "select * from fees f join cities c on f.id_source_city = c.id where c.name = ?1", nativeQuery = true)
    List<Fee> findBySourceCity(String city);

    @Query(value = "select * from fees f join cities c on f.id_destination_city = c.id where c.name = ?1", nativeQuery = true)
    List<Fee> findByDestinationCity(String city);

    @Query(value = "call backoffice_request_fee_by_id(:idCityFrom, :idCityTo);", nativeQuery = true)
    FeeRequest getFeeByIdCities(@Param("idCityFrom")Integer idCityFrom, @Param("idCityTo")Integer idCityTo);

    @Query(value = "call backoffice_request_fee(:cityFrom, :cityTo);", nativeQuery = true)
    FeeRequest getFeeByNameCities(@Param("cityFrom")String cityFrom, @Param("cityTo")String cityTo);
}
