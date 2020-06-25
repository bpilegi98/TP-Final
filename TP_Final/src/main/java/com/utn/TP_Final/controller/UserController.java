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
import org.springframework.web.bind.annotation.*;

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
    public void addUser(@RequestBody User newUser) throws UserAlreadyExistsException, ValidationException
    {
        userService.addUser(newUser);
    }

    @PostMapping("/delete/{dni}")
    public void removeUser(@RequestBody(required = true)String dni)throws UserNotExistsException, ValidationException
    {
        userService.deleteUser(dni);
    }


    @GetMapping("/")
    public List<User> getAll(@RequestParam(required = false) String name)
    {
        return userService.getAll(name);
    }

    @GetMapping("/getByDni/{dni}")
    public User getByDni(@RequestParam(required = true)String dni) throws UserNotExistsException, ValidationException
    {
        return userService.getByDni(dni);
    }

    @GetMapping("/getByLineNumber/{lineNumber}")
    public User getByLineNumber(@RequestParam(required = true)String lineNumber)throws UserNotExistsException, ValidationException
    {
        return userService.getByLineNumber(lineNumber);
    }

    @GetMapping("/getById/{id}")
    public Optional<User> getById(@RequestParam(required = true)Integer id)throws UserNotExistsException, ValidationException
    {
        return userService.getById(id);
    }

    @GetMapping("/getByUsername/{username}")
    public User getByUsername(@RequestParam(required = true)String username, @RequestParam(required = true)String password) throws UserNotExistsException, ValidationException
    {
        return userService.getByUsername(username, password);
    }

    @PostMapping("/login")
    public User login(@RequestBody String username, @RequestBody String password) throws UserNotExistsException, ValidationException
    {
        if((username != null) && (password != null))
        {
            return userService.login(username, password);
        }else{
            throw new ValidationException("You must complete the fields.");
        }
    }

    @GetMapping("/getCallsBetweenDates")
    public List<CallsBetweenDates> getCallsBetweenDates(@PathVariable Date from, @PathVariable Date to, Integer idLoggedUser) throws DateNotExistsException, ValidationException
    {
        return userService.getCallsBetweenDates(from, to, idLoggedUser);
    }

    @GetMapping("/getInvoicesBetweenDates")
    public List<InvoicesBetweenDatesUser> getInvoicesBetweenDates(@PathVariable Date from, @PathVariable Date to, Integer idLoggedUser)throws DateNotExistsException, ValidationException
    {
        return userService.getInvoicesBetweenDates(from, to, idLoggedUser);
    }


    @GetMapping("/getTopMostCalledDestinations")
    public List<TopMostCalledDestinations> getTopMostCalledDestinations(Integer idLoggedUser)throws UserNotExistsException, ValidationException
    {
        return userService.getTopMostCalledDestinations(idLoggedUser);
    }


}
