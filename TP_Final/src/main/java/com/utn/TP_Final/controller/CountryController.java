package com.utn.TP_Final.controller;


import com.utn.TP_Final.model.Country;
import com.utn.TP_Final.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("")
@RequestMapping("/country")
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping("/")
    public void addCountry(@RequestBody Country newCountry)
    {
        countryService.addCountry(newCountry);
    }

    @GetMapping("/")
    public List<Country> getAll(@RequestParam(required = false) String name)
    {
        return countryService.getAll(name);
    }
}
