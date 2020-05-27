package com.utn.TP_Final.controller;


import com.utn.TP_Final.exceptions.PersonAlreadyExistsException;
import com.utn.TP_Final.exceptions.PersonNotExistsException;
import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.model.Person;
import com.utn.TP_Final.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("")
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }



    @PostMapping("/")
    public void addPerson(@RequestBody Person newPerson) throws PersonAlreadyExistsException
    {
        personService.addPerson(newPerson);
    }

    @PostMapping("/delete/{dni}")
    public void removePerson(@RequestBody(required = true)Integer dni)
    {
        personService.deletePerson(dni);
    }


    @GetMapping("/")
    public List<Person> getAll(@RequestParam(required = false) String name)
    {
        return personService.getAll(name);
    }

    @GetMapping("/getByDni/{dni}")
    public Person getByDni(@RequestParam(required = true)Integer dni)
    {
        return personService.getByDni(dni);
    }

    @GetMapping("/getByLineNumber/{lineNumber}")
    public Person getByLineNumber(@RequestParam(required = true)String lineNumber)
    {
        return personService.getByLineNumber(lineNumber);
    }

    @GetMapping("/getById/{id}")
    public Optional<Person> getById(@RequestParam(required = true)Integer id)
    {
        return personService.getById(id);
    }

    @GetMapping("/getByUsername/{username}")
    public Person getByUsername(@RequestParam(required = true)String username, @RequestParam(required = true)String password){
        return personService.getByUsername(username, password);
    }

    @PostMapping("/login")
    public Person login(@RequestBody String username, @RequestBody String password) throws PersonNotExistsException, ValidationException
    {
        if((username != null) && (password != null))
        {
            return personService.login(username, password);
        }else{
            throw new ValidationException("You must complete the fields.");
        }
    }
}
