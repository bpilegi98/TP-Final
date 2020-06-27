package com.utn.TP_Final.controller;


import com.utn.TP_Final.exceptions.DateNotExistsException;
import com.utn.TP_Final.exceptions.UserAlreadyExistsException;
import com.utn.TP_Final.exceptions.UserNotExistsException;
import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.model.User;
import com.utn.TP_Final.projections.CallsBetweenDates;
import com.utn.TP_Final.projections.InvoicesBetweenDatesUser;
import com.utn.TP_Final.projections.TopMostCalledDestinations;
import com.utn.TP_Final.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController("")
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping("/")
    public void addUser(@RequestBody User newUser) throws UserAlreadyExistsException, ValidationException, InvalidKeySpecException, NoSuchAlgorithmException {
        userService.addUser(newUser);
    }

    @PostMapping("/delete/{dni}")
    public String removeUser(@PathVariable String dni)throws UserNotExistsException, ValidationException
    {
        return userService.deleteUser(dni);
    }


    @GetMapping("/")
    public List<User> getAll(@RequestParam(required = false) String name)
    {
        return userService.getAll(name);
    }

    @GetMapping("/dni/{dni}")
    public User getByDni(@PathVariable String dni) throws UserNotExistsException, ValidationException
    {
        return userService.getByDni(dni);
    }

    @GetMapping("/{id}")
    public Optional<User> getById(@PathVariable Integer id)throws UserNotExistsException, ValidationException
    {
        return userService.getById(id);
    }

    @GetMapping("/username/{username}/{password}")
    public User getByUsername(@PathVariable String username) throws UserNotExistsException, ValidationException
    {
        return userService.getByUsername(username);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody String username, @RequestBody String password) throws UserNotExistsException, ValidationException, InvalidKeySpecException, NoSuchAlgorithmException {
        if((username != null) && (password != null))
        {
            return userService.login(username, password);
        }else{
            throw new ValidationException("You must complete the fields.");
        }
    }

    @GetMapping("/getCallsBetweenDates/{from}/{to}")
    public List<CallsBetweenDates> getCallsBetweenDates(@PathVariable Date from, @PathVariable Date to, Integer idLoggedUser) throws DateNotExistsException, ValidationException, UserNotExistsException {
        return userService.getCallsBetweenDates(from, to, idLoggedUser);
    }

    @GetMapping("/getInvoicesBetweenDates/{from}/{to}")
    public List<InvoicesBetweenDatesUser> getInvoicesBetweenDates(@PathVariable Date from, @PathVariable Date to, Integer idLoggedUser) throws DateNotExistsException, ValidationException, UserNotExistsException {
        return userService.getInvoicesBetweenDates(from, to, idLoggedUser);
    }


    @GetMapping("/getTopMostCalledDestinations")
    public List<TopMostCalledDestinations> getTopMostCalledDestinations(Integer idLoggedUser)throws UserNotExistsException, ValidationException
    {
        return userService.getTopMostCalledDestinations(idLoggedUser);
    }


}
