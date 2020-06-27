package com.utn.TP_Final.controller;

import com.utn.TP_Final.exceptions.UserAlreadyExistsException;
import com.utn.TP_Final.exceptions.UserNotExistsException;
import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.model.City;
import com.utn.TP_Final.model.User;
import com.utn.TP_Final.projections.CallsBetweenDates;
import com.utn.TP_Final.projections.InvoicesBetweenDatesUser;
import com.utn.TP_Final.projections.TopMostCalledDestinations;
import com.utn.TP_Final.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }



    public ResponseEntity<User> addUser(@RequestBody User newUser) throws UserAlreadyExistsException, ValidationException, InvalidKeySpecException, NoSuchAlgorithmException
    {
        return ResponseEntity.created(getUri(userService.addUser(newUser))).build();
    }


    public ResponseEntity<User> removeUser(@PathVariable String dni)throws UserNotExistsException, ValidationException
    {
        return ResponseEntity.ok(userService.deleteUser(dni));
    }


    public ResponseEntity<List<User>> getAll(@RequestParam(required = false) String name)
    {
        return ResponseEntity.ok(userService.getAll(name));
    }


    public ResponseEntity<User> getByDni(@PathVariable String dni) throws UserNotExistsException, ValidationException
    {
        return ResponseEntity.ok(userService.getByDni(dni));
    }


    public ResponseEntity<Optional<User>> getById(@PathVariable Integer id)throws UserNotExistsException, ValidationException
    {
        return ResponseEntity.ok(userService.getById(id));
    }


    public ResponseEntity<User> getByUsername(@PathVariable String username) throws UserNotExistsException, ValidationException
    {
        return ResponseEntity.ok(userService.getByUsername(username));
    }


    public ResponseEntity<User> login(@RequestBody String username, @RequestBody String password) throws UserNotExistsException, ValidationException, InvalidKeySpecException, NoSuchAlgorithmException {
        if((username != null) && (password != null))
        {
            return userService.login(username, password);
        }else{
            throw new ValidationException("You must complete the fields.");
        }
    }


    public ResponseEntity<List<CallsBetweenDates>> getCallsBetweenDates(@PathVariable Date from, @PathVariable Date to, Integer idLoggedUser) throws  ValidationException, UserNotExistsException {
        return ResponseEntity.ok(userService.getCallsBetweenDates(from, to, idLoggedUser));
    }


    public ResponseEntity<List<InvoicesBetweenDatesUser>> getInvoicesBetweenDates(@PathVariable Date from, @PathVariable Date to, Integer idLoggedUser) throws  ValidationException, UserNotExistsException {
        return ResponseEntity.ok(userService.getInvoicesBetweenDates(from, to, idLoggedUser));
    }


    public ResponseEntity<List<TopMostCalledDestinations>> getTopMostCalledDestinations(Integer idLoggedUser)throws UserNotExistsException, ValidationException
    {
        return ResponseEntity.ok(userService.getTopMostCalledDestinations(idLoggedUser));
    }

    private URI getUri(User user)
    {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}/")
                .buildAndExpand(user.getId())
                .toUri();
    }


}
