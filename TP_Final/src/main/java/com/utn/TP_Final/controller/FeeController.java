package com.utn.TP_Final.controller;


import com.utn.TP_Final.exceptions.CityNotExistsException;
import com.utn.TP_Final.exceptions.FeeAlreadyExistsException;
import com.utn.TP_Final.exceptions.FeeNotExistsException;
import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.model.Fee;
import com.utn.TP_Final.projections.FeeRequest;
import com.utn.TP_Final.service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("")
@RequestMapping("/fee")
public class FeeController {

    private final FeeService feeService;

    @Autowired
    public FeeController(FeeService feeService) {
        this.feeService = feeService;
    }

    @PostMapping("/")
    public Fee addFee(@RequestBody Fee newFee) throws FeeAlreadyExistsException {
        return feeService.addFee(newFee);
    }

    @PostMapping("/delete/{id}")
    public Fee deleteFee(Integer id) throws FeeNotExistsException {
        return feeService.deleteFee(id);
    }

    @GetMapping("/")
    public List<Fee> getAll()
    {
        return feeService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Fee> getById(@PathVariable Integer id) throws FeeNotExistsException {
        return feeService.getById(id);
    }

    @GetMapping("/sourceCity/{sourceCityName}")
    public List<Fee> getBySourceCity(@PathVariable String cityName) throws CityNotExistsException, ValidationException, FeeNotExistsException {
        return feeService.getBySourceCity(cityName);
    }

    @GetMapping("/destinationCity/{destinationCityName}")
    public List<Fee> getByDestinationCity(@PathVariable String cityName) throws CityNotExistsException, ValidationException, FeeNotExistsException {
        return feeService.getByDestinationCity(cityName);
    }

    @GetMapping("/idCities/{idCityFrom}/{idCityTo}")
    public FeeRequest getFeeByIdCities(@PathVariable Integer idCityFrom, @PathVariable Integer idCityTo) throws CityNotExistsException, ValidationException, FeeNotExistsException {
        return feeService.getFeeByIdCities(idCityFrom, idCityTo);
    }

    @GetMapping("/nameCities/{cityFrom}/{cityTo}")
    public FeeRequest getFeeByNameCities(@PathVariable String cityFrom, @PathVariable String cityTo) throws CityNotExistsException, ValidationException, FeeNotExistsException {
        return feeService.getFeeByNameCities(cityFrom, cityTo);
    }
}
