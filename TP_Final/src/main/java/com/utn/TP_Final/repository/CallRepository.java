package com.utn.TP_Final.repository;


import com.utn.TP_Final.model.Call;
import com.utn.TP_Final.projections.CallsFromUser;
import com.utn.TP_Final.projections.CallsFromUserSimple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface CallRepository extends JpaRepository<Call, Integer> {

    @Query(value = "call backoffice_request_calls_user_simple(:dni);", nativeQuery = true)
    CallsFromUserSimple getCallsFromUserSimple(@Param("dni")String dni);

    @Query(value = "call backoffice_request_calls_user(:dni);", nativeQuery = true)
    List<CallsFromUser> getCallsFromUser(@Param("dni")String dni);

    @Modifying
    @Query(value= "insert into calls (source_number, destination_number,duration_secs,date_call) value (?1,?2,?3,?4)",nativeQuery = true)
    void addCall(String sourceNumber,String destinationNumber,Integer duration,Date date);


}
