package com.utn.TP_Final.repository;


import com.utn.TP_Final.model.Call;
import com.utn.TP_Final.projections.CallsFromUserSimple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CallRepository extends JpaRepository<Call, Integer> {

    @Query(value = "call backoffice_request_calls_user_simple(:dni);", nativeQuery = true)
    CallsFromUserSimple getCallsFromUserSimple(@Param("dni")String dni);

    @Query(value = "select * from calls c inner join telephone_lines t on c.id_source_number = t.id " +
            " inner join users u on t.id_user = u.id where u.dni = ?1", nativeQuery = true)
    List<Call> getCallsFromUser(String dni);

    @Query(value = "remove from calls where id = ?1", nativeQuery = true)
    Call delete(Integer id);



}
