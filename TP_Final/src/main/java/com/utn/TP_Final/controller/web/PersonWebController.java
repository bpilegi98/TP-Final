package com.utn.TP_Final.controller.web;


import com.utn.TP_Final.controller.PersonController;
import com.utn.TP_Final.exceptions.PersonAlreadyExistsException;
import com.utn.TP_Final.exceptions.PersonNotExistsException;
import com.utn.TP_Final.model.Person;
import com.utn.TP_Final.session.SessionManager;
import org.aspectj.weaver.patterns.PerObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person")
public class PersonWebController {

    private final PersonController personController;
    private final SessionManager sessionManager;

    @Autowired
    public PersonWebController(PersonController personController, SessionManager sessionManager) {
        this.personController = personController;
        this.sessionManager = sessionManager;
    }

    @GetMapping
    public ResponseEntity<List<Person>> getPersons(@RequestHeader("Authorization") String sessionToken)
    {
        Person currentUser = sessionManager.getLoggedUser(sessionToken);
        if(currentUser == null || currentUser.getUserType().equals("CUSTOMER"))
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<Person> persons = personController.getAll(null); //asi esta bien pasar el param?
        return (persons.size() > 0) ? ResponseEntity.ok(persons) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PostMapping("/add")
    public ResponseEntity newPerson(@RequestHeader("Authorization") String sessionToken, @RequestBody Person person) throws PersonAlreadyExistsException
    {
        Person currentUser = sessionManager.getLoggedUser(sessionToken);
        if(currentUser == null || currentUser.getUserType().equals("CUSTOMER")) //si solo el admin puede dar de alta y baja datos y los employee solo modifican agregar el usertype a la condicion
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        personController.addPerson(person);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/delete")
    public ResponseEntity deletePerson(@RequestHeader("Authorization")String sessionToken, @RequestBody Integer dni) throws PersonNotExistsException
    {
        Person currentUser = sessionManager.getLoggedUser(sessionToken);
        if(currentUser == null || currentUser.getUserType().equals("CUSTOMER"))
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        personController.removePerson(dni);
        return ResponseEntity.status(HttpStatus.GONE).build();
    }
}
