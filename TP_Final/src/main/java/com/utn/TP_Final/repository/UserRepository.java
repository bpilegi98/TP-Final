package com.utn.TP_Final.repository;

import com.utn.TP_Final.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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

}
