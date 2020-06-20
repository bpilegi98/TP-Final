package com.utn.TP_Final.controller;


import com.utn.TP_Final.exceptions.UserAlreadyExistsException;
import com.utn.TP_Final.exceptions.UserNotExistsException;
import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.model.User;
import com.utn.TP_Final.projections.CallsBetweenDates;
import com.utn.TP_Final.projections.InvoicesBetweenDates;
import com.utn.TP_Final.projections.TopMostCalledDestinations;
import com.utn.TP_Final.service.UserService;
import com.utn.TP_Final.session.Session;
import com.utn.TP_Final.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

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
    public void addUser(@RequestBody User newUser) throws UserAlreadyExistsException
    {
        userService.addUser(newUser);
    }

    @PostMapping("/delete/{dni}")
    public void removeUser(@RequestBody(required = true)String dni)
    {
        userService.deleteUser(dni);
    }


    @GetMapping("/")
    public List<User> getAll(@RequestParam(required = false) String name)
    {
        return userService.getAll(name);
    }

    @GetMapping("/getByDni/{dni}")
    public User getByDni(@RequestParam(required = true)String dni)
    {
        return userService.getByDni(dni);
    }

    @GetMapping("/getByLineNumber/{lineNumber}")
    public User getByLineNumber(@RequestParam(required = true)String lineNumber)
    {
        return userService.getByLineNumber(lineNumber);
    }

    @GetMapping("/getById/{id}")
    public Optional<User> getById(@RequestParam(required = true)Integer id)
    {
        return userService.getById(id);
    }

    @GetMapping("/getByUsername/{username}")
    public User getByUsername(@RequestParam(required = true)String username, @RequestParam(required = true)String password){
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
    public List<CallsBetweenDates> getCallsBetweenDates(@PathVariable Date from, @PathVariable Date to, Integer idLoggedUser)
    {
        return userService.getCallsBetweenDates(from, to, idLoggedUser);
    }

    @GetMapping("/getInvoicesBetweenDates")
    public List<InvoicesBetweenDates> getInvoicesBetweenDates(@PathVariable Date from, @PathVariable Date to, Integer idLoggedUser)
    {
        return userService.getInvoicesBetweenDates(from, to, idLoggedUser);
    }


    @GetMapping("/getTopMostCalledDestinations")
    public List<TopMostCalledDestinations> getTopMostCalledDestinatons(Integer idLoggedUser)
    {
        return userService.getTopMostCalledDestinationds(idLoggedUser);
    }


}
