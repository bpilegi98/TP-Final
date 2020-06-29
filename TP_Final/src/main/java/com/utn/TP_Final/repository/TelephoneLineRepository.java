package com.utn.TP_Final.repository;

import com.utn.TP_Final.model.TelephoneLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TelephoneLineRepository extends JpaRepository<TelephoneLine, Integer> {

    @Query(value = "select * from telephone_lines where line_number = ?1", nativeQuery = true)
    TelephoneLine findByLineNumber(String lineNumber);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update telephone_lines set status = 'SUSPENDED' where line_number = ?1", nativeQuery = true)
    int suspendTelephoneLine(String lineNumber);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update telephone_lines set status = 'ACTIVE' where line_number = ?1", nativeQuery = true)
    int activeTelephoneLine(String lineNumber);

}
