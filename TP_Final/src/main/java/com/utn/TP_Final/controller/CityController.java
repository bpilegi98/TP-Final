package com.utn.TP_Final.controller;

import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.model.City;
import com.utn.TP_Final.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Controller
public class CityController {

    private final CityService cityService;


    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }



    public ResponseEntity<City> addCity(@RequestBody City newCity) throws ValidationException
    {
        return ResponseEntity.created(getUri(cityService.addCity(newCity))).build();
    }


    public ResponseEntity<City> deleteCity(@RequestParam Integer id) throws ValidationException {
        City city = cityService.deleteCity(id);
        return ResponseEntity.ok().build();
    }


    public ResponseEntity<List<City>> getAll(@RequestParam(required = false)String name)
    {
        return ResponseEntity.ok(cityService.getAll(name));
    }


    public ResponseEntity<Optional<City>> getById(@PathVariable Integer id) throws ValidationException
    {
        return ResponseEntity.ok(cityService.getById(id));
    }


    public ResponseEntity<City> getByPrefix(@PathVariable String prefix) throws ValidationException {
        return ResponseEntity.ok(cityService.getByPrefix(prefix));
    }


    @GetMapping("/script")
    public void uploadCities() throws IOException {
        cityService.uploadCites();
    }

    private URI getUri(City city)
    {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}/")
                .buildAndExpand(city.getId())
                .toUri();
    }


}
