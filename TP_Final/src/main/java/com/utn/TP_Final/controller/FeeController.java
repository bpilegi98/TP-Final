package com.utn.TP_Final.controller;


import com.utn.TP_Final.model.Fee;
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

    @GetMapping("/{id}")
    public Optional<Fee> getById(@RequestParam(required = true)Integer id)
    {
        return feeService.getById(id);
    }

    @GetMapping("/{sourceCityName}")
    public List<Fee> getBySourceCity(@RequestParam(required = true)String cityName)
    {
        return feeService.getBySourceCity(cityName);
    }

    @GetMapping("/{destinationCityName}")
    public List<Fee> getByDestinationCity(@RequestParam(required = true)String cityName)
    {
        return feeService.getByDestinationCity(cityName);
    }

    @GetMapping("/{prefixNumber}")
    public Fee getByPrefix(@RequestParam(required = true)Integer prefixNumber)
    {
        return feeService.getByPrefix(prefixNumber);
    }
}
