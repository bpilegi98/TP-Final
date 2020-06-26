package com.utn.TP_Final.repository;

import com.utn.TP_Final.model.TelephoneLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TelephoneLineRepository extends JpaRepository<TelephoneLine, Integer> {

    @Query(value = "select * from telephone_lines where line_number = ?1", nativeQuery = true)
    TelephoneLine findByLineNumber(String lineNumber);

    @Query(value = "remove from telephone_lines where line_number = ?1", nativeQuery = true)
    TelephoneLine delete(String lineNumber);

    @Query(value = "update telephone_lines set status = 'SUSPENDED' where line_number = ?1", nativeQuery = true)
    TelephoneLine suspendTelephoneLine(String lineNumber);

    @Query(value = "update telephone_lines set status = 'ACTIVE' where line_number = ?1", nativeQuery = true)
    TelephoneLine activeTelephoneLine(String lineNumber);

}
