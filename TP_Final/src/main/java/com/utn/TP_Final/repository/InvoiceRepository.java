package com.utn.TP_Final.repository;

import com.utn.TP_Final.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    //hacer @Query que devuelva los invoice de entre dos fechas // FIJARSE QUE FUNCIONE BIEN
    //@Query(value = "select * from invoices where dateCreation = ?1 and dateExpiration = ?2")
    //List<Invoice> findBetweenDates(Date from, Date to);
}
