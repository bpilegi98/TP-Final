package com.utn.TP_Final.controller;


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
    public void addPerson(@RequestBody Person newPerson)
    {
        personService.addPerson(newPerson);
    }

    @GetMapping("/")
    public List<Person> getAll(@RequestParam(required = false) String name)
    {
        return personService.getAll(name);
    }

    @GetMapping("/{dni}")
    public Person getByDni(@RequestParam(required = true)Integer dni)
    {
        return personService.getByDni(dni);
    }

    @GetMapping("/{lineNumber}")
    public Person getByLineNumber(@RequestParam(required = true)String lineNumber)
    {
        return personService.getByLineNumber(lineNumber);
    }

    @GetMapping("/{id}")
    public Optional<Person> getById(@RequestParam(required = true)Integer id)
    {
        return personService.getById(id);
    }
}
