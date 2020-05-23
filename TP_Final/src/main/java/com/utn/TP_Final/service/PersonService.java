package com.utn.TP_Final.service;

import com.utn.TP_Final.exceptions.PersonAlreadyExistsException;
import com.utn.TP_Final.exceptions.PersonNotExistsException;
import com.utn.TP_Final.model.Person;
import com.utn.TP_Final.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    public void addPerson(Person newPerson) { //esto vendria a ser como el registro de usuarios
        personRepository.save(newPerson);
    }

    public void deletePerson(Person person)
    {
        personRepository.delete(person);
    } //esto es como el remove user

    public List<Person> getAll(String name) {
        if(isNull(name))
        {
            return personRepository.findAll();
        }
        return personRepository.findByLastname(name);
    }

    public Person getByDni(Integer dni)
    {
        return personRepository.findByDni(dni);
    }

    public Person getByLineNumber(String lineNumber)
    {
        return personRepository.findByLineNumber(lineNumber);
    }

    public Optional<Person> getById(Integer id)
    {
        return personRepository.findById(id);
    }

    public Person getByUsername(String username, String password)
    {
        return personRepository.findByUsername(username, password);
    }

    public Person login(String username, String password) throws PersonNotExistsException{
        Person person = personRepository.findByUsername(username, password);
        return Optional.ofNullable(person).orElseThrow(()-> new PersonNotExistsException());
    }

}
