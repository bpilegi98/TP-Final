package com.utn.TP_Final.repository;

import com.utn.TP_Final.model.User;
import com.utn.TP_Final.projections.CallsBetweenDates;
import com.utn.TP_Final.projections.InvoicesBetweenDatesUser;
import com.utn.TP_Final.projections.TopMostCalledDestinations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select * from users where lastname = ?1", nativeQuery = true)
    List<User> findByLastname(String name);

    @Query(value = "select * from users where dni = ?1", nativeQuery = true)
    User findByDni(String dni);

    @Query(value = "select * from users u join telephone_lines t on u.id = t.id_user where line_number = ?1", nativeQuery = true)
    User findByLineNumber(String lineNumber);

    @Query(value = "select * from users where username = ?1 and password = ?2", nativeQuery = true)
    User findByUsername(String username, String password);

    @Query(value = "remove from users where dni = ?1", nativeQuery = true)
    void delete(String dni);

    //averiguar el tema de manejo de fechas q chocan los formatos
    @Query(value = "call user_calls_between_dates(:fromD, :toD, :idLoggedUser);", nativeQuery = true)
    List<CallsBetweenDates> getCallsBetweenDates(@Param("fromD") Date from, @Param("toD") Date to, @Param("idLoggedUser") Integer idLoggedUser); //agregar exceptions correspondientes

    @Query(value = "call user_invoices_between_dates(:fromD, :toD, :idLoggedUser);", nativeQuery = true)
    List<InvoicesBetweenDatesUser> getInvoicesBetweenDates(@Param("fromD") Date from, @Param("toD") Date to, @Param("idLoggedUser")Integer idLoggedUser);

    @Query(value = "call user_top_most_called(:idLoggedUser);", nativeQuery = true)
    List<TopMostCalledDestinations> getTopMostCalledDestinations(@Param("idLoggedUser") Integer idLoggedUser);
}
