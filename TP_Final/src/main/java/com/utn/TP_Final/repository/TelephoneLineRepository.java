package com.utn.TP_Final.repository;

import com.utn.TP_Final.model.TelephoneLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TelephoneLineRepository extends JpaRepository<TelephoneLine, Integer> {

    @Query(value = "select * from TelephoneLines where lineNumber = ?1", nativeQuery = true)
    List<TelephoneLine> findByLineNumber(String lineNumber);

    @Query(value = "remove from telephonelines where linenumber = ?1", nativeQuery = true)
    void delete(String lineNumber);

    @Query(value = "update telephonelines set linestatus = 'SUSPENDED' where linenumber = ?1", nativeQuery = true)
    void suspendTelephoneLine(String lineNumber);

    @Query(value = "update telephonelines set linestatus = 'ACTIVE' where linenumber = ?1", nativeQuery = true)
    void activeTelephoneLine(String lineNumber);
}
