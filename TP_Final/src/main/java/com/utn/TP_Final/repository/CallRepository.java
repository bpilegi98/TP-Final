package com.utn.TP_Final.repository;


import com.utn.TP_Final.dto.CallsUserDto;
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

    @Query(value = "select new CallsUserDto ( ca.source_number as sourceNumber , (select name from cities where id=ca.id_source_number) as sourceCity ,  " +
            " ca.destination_number as destinationNumber , (select name from cities where id=ca.id_destination_number) as destinationCity ,  " +
            " ca.total_price as totalPrice, ca.date_call as dateCall) " +
            " from calls ca " +
            " inner join telephone_lines t " +
            " on ca.source_number = t.line_number " +
            " inner join users u " +
            " on t.id_user = u.id " +
            " where u.dni = ?1", nativeQuery = true)
    List<CallsUserDto> getCallsFromUser(String dni);

    @Query(value = "remove from calls where id = ?1", nativeQuery = true)
    Call delete(Integer id);

    /*
    @Modifying
    @Query(value = "call add_call_aerial(:sourceNumber, :destinationNumber, :duration, :dateCall)", nativeQuery = true)
    void addCallDto(@Param("sourceNumber")String sourceNumber, @Param("destinationNumber")String destinationNumber, @Param("duration")Integer duration, @Param("dateCall")Date date);
     */

}
