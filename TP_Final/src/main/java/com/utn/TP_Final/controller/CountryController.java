package com.utn.TP_Final.controller;


import com.utn.TP_Final.exceptions.CountryNotExistsException;
import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.model.Country;
import com.utn.TP_Final.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("")
@RequestMapping("/country")
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping(value= "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void addCountry(@RequestBody Country newCountry)
    {
        countryService.addCountry(newCountry);
    }

    @GetMapping("/")
    public List<Country> getAll(@RequestParam(required = false) String name)
    {
        return countryService.getAll(name);
    }

    @GetMapping("/{id}")
    public Optional<Country> getById(@RequestParam(required = true)Integer id) throws CountryNotExistsException, ValidationException
    {
        try {
            return countryService.findById(id);
        }
        catch (CountryNotExistsException e)
        {
            throw new ValidationException(e.getMessage());
        }
    }
}
