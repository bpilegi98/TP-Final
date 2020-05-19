package com.utn.TP_Final.repository;


import com.utn.TP_Final.model.Call;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CallRepository extends JpaRepository<Call, Integer> {

   // @Query(value = "select * from calls c join ")
   // List<Call> getBySourceNumber(String number);

    //Resolver como hacer relacion calls telephoneLine
}
