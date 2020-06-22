package com.utn.TP_Final.controller;


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
    public void addFee(@RequestBody Fee newFee)
    {
        feeService.addFee(newFee);
    }

    @GetMapping("/")
    public List<Fee> getAll()
    {
        return feeService.getAll();
    }

    @GetMapping("/getById/{id}")
    public Optional<Fee> getById(@RequestParam(required = true)Integer id)
    {
        return feeService.getById(id);
    }

    @GetMapping("/getBySourceCity/{sourceCityName}")
    public List<Fee> getBySourceCity(@RequestParam(required = true)String cityName)
    {
        return feeService.getBySourceCity(cityName);
    }

    @GetMapping("/getByDestinationCity/{destinationCityName}")
    public List<Fee> getByDestinationCity(@RequestParam(required = true)String cityName)
    {
        return feeService.getByDestinationCity(cityName);
    }

    @GetMapping("/getFeeByIdCities/{idCityFrom}/{idCityTo}")
    public FeeRequest getFeeByIdCities(@PathVariable Integer idCityFrom, @PathVariable Integer idCityTo)
    {
        return feeService.getFeeByIdCities(idCityFrom, idCityTo);
    }

    @GetMapping("/getFeeByNameCities/{cityFrom}/{cityTo}")
    public FeeRequest getFeeByNameCities(@PathVariable String cityFrom, @PathVariable String cityTo)
    {
        return feeService.getFeeByNameCities(cityFrom, cityTo);
    }
}
