package com.utn.TP_Final.repository;

import com.utn.TP_Final.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer> {

   // @Query(value = "select * from provinces where name = ?1", nativeQuery = true)
    List<Province> findByName(String name);
}
