package com.utn.TP_Final.controller.web;


import com.utn.TP_Final.controller.UserController;
import com.utn.TP_Final.exceptions.UserAlreadyExistsException;
import com.utn.TP_Final.exceptions.UserNotExistsException;
import com.utn.TP_Final.model.User;
import com.utn.TP_Final.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person")
public class PersonWebController {

    private final UserController userController;
    private final SessionManager sessionManager;

    @Autowired
    public PersonWebController(UserController userController, SessionManager sessionManager) {
        this.userController = userController;
        this.sessionManager = sessionManager;
    }

    @GetMapping
    public ResponseEntity<List<User>> getPersons(@RequestHeader("Authorization") String sessionToken)
    {
        User currentUser = sessionManager.getLoggedUser(sessionToken);
        if(currentUser == null || currentUser.getUserType().equals("CUSTOMER"))
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<User> users = userController.getAll(null); //asi esta bien pasar el param?
        return (users.size() > 0) ? ResponseEntity.ok(users) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PostMapping("/add")
    public ResponseEntity newPerson(@RequestHeader("Authorization") String sessionToken, @RequestBody User user) throws UserAlreadyExistsException
    {
        User currentUser = sessionManager.getLoggedUser(sessionToken);
        if(currentUser == null || currentUser.getUserType().equals("CUSTOMER")) //si solo el admin puede dar de alta y baja datos y los employee solo modifican agregar el usertype a la condicion
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        userController.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/delete")
    public ResponseEntity deletePerson(@RequestHeader("Authorization")String sessionToken, @RequestBody String dni) throws UserNotExistsException
    {
        User currentUser = sessionManager.getLoggedUser(sessionToken);
        if(currentUser == null || currentUser.getUserType().equals("CUSTOMER"))
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        userController.removeUser(dni);
        return ResponseEntity.status(HttpStatus.GONE).build();
    }
}
