package com.utn.TP_Final.repository;

import com.utn.TP_Final.model.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeeRepository extends JpaRepository<Fee, Integer> {

    //hacer query que devuelva fee por ciudad de origen/destino ?
}
