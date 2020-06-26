package com.utn.TP_Final.controller;

import com.utn.TP_Final.exceptions.CityAlreadyExistsException;
import com.utn.TP_Final.exceptions.CityNotExistsException;
import com.utn.TP_Final.model.City;
import com.utn.TP_Final.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController("")
@RequestMapping("/city")
public class CityController {

    private final CityService cityService;


    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping("/")
    public City addCity(@RequestBody City newCity) throws CityAlreadyExistsException {
       return cityService.addCity(newCity);
    }

    @PostMapping("/delete/{id}")
    public City deleteCity(@RequestParam Integer id) throws CityNotExistsException {
        return cityService.deleteCity(id);
    }

    @GetMapping("/")
    public List<City> getAll(@RequestParam(required = false)String name)
    {
        return cityService.getAll(name);
    }

    @GetMapping("/{id}")
    public Optional<City> getById(@PathVariable Integer id) throws CityNotExistsException {
        return cityService.getById(id);
    }

    @GetMapping("/prefix/{prefix}")
    public City getByPrefix(@PathVariable String prefix) throws CityNotExistsException {
        return cityService.getByPrefix(prefix);
    }

    /* Método cargar ciudades
    @GetMapping("/script")
    public void uploadCities() throws IOException {
        cityService.uploadCites();
    }
     */
}
