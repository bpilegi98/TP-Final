package com.utn.TP_Final.repository;


import com.utn.TP_Final.model.Call;
import com.utn.TP_Final.model.Fee;
import com.utn.TP_Final.projections.CallsFromUser;
import com.utn.TP_Final.projections.CallsFromUserSimple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Date;
import java.util.List;

@Repository
public interface CallRepository extends JpaRepository<Call, Integer> {

    @Query(value = "call backoffice_request_calls_user_simple(:dni);", nativeQuery = true)
    CallsFromUserSimple getCallsFromUserSimple(@Param("dni")String dni);

    @Query(value = "call backoffice_request_calls_user(:dni);", nativeQuery = true)
    List<CallsFromUser> getCallsFromUser(@Param("dni")String dni);

    @Query(value = "remove from calls where id = ?1", nativeQuery = true)
    Call delete(Integer id);

    /*
    @Modifying
    @Query(value = "call add_call_aerial(:sourceNumber, :destinationNumber, :duration, :dateCall)", nativeQuery = true)
    void addCallDto(@Param("sourceNumber")String sourceNumber, @Param("destinationNumber")String destinationNumber, @Param("duration")Integer duration, @Param("dateCall")Date date);
     */

}
