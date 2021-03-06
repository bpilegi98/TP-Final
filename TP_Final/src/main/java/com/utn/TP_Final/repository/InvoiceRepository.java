package com.utn.TP_Final.repository;

import com.utn.TP_Final.model.Call;
import com.utn.TP_Final.model.Invoice;
import com.utn.TP_Final.projections.InvoiceIncome;
import com.utn.TP_Final.projections.InvoicesFromUser;
import com.utn.TP_Final.projections.InvoicesRequestFromPeriods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;


@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    @Query(value = "call backoffice_invoices_from_user(:dni);", nativeQuery = true)
    List<InvoicesFromUser> getInvoicesFromUser(@Param("dni")String dni);

    @Query(value = "remove from invoices where id = ?1", nativeQuery = true)
    Invoice delete(Integer id);

    @Query(value = "call backoffice_invoices_from_user_paid(:dni);", nativeQuery = true)
    List<InvoicesFromUser> getInvoicesPaidFromUser(@Param("dni")String dni);

    @Query(value = "call backoffice_invoices_from_user_not_paid(:dni);", nativeQuery = true)
    List<InvoicesFromUser> getInvoicesNotPaidFromUser(@Param("dni")String dni);

    @Query(value = "call backoffice_invoices_from_month(:month, :year);", nativeQuery = true)
    List<InvoicesRequestFromPeriods> getInvoicesFromMonth(@Param("month")String month,@Param("year") String year);

    @Query(value = "call backoffice_invoices_from_year(:year);", nativeQuery = true)
    List<InvoicesRequestFromPeriods> getInvoicesFromYear(@Param("year")String year);

    @Query(value = "call backoffice_invoices_between_dates(:from, :to);", nativeQuery = true)
    List<InvoicesRequestFromPeriods> getInvoicesBetweenDates(@Param("from")Date from, @Param("to")Date to);

    @Query(value = "call backoffice_check_income();", nativeQuery = true)
    InvoiceIncome getIncome();

    @Query(value = "call backoffice_check_income_month(:month,:year);", nativeQuery = true)
    InvoiceIncome getIncomeMonth(@Param("month")String month,@Param("year")String year);

    @Query(value = "call backoffice_check_income_year(:year);", nativeQuery = true)
    InvoiceIncome getIncomeYear(@Param("year")String year);
}
