package com.utn.TP_Final.controller;


import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.model.Fee;
import com.utn.TP_Final.projections.FeeRequest;
import com.utn.TP_Final.service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Controller
public class FeeController {

    private final FeeService feeService;

    @Autowired
    public FeeController(FeeService feeService)
    {
        this.feeService = feeService;
    }


    public ResponseEntity<Fee> addFee(@RequestBody Fee newFee) throws ValidationException
    {
        return ResponseEntity.created(getUri(feeService.addFee(newFee))).build();
    }


    public ResponseEntity<Fee> deleteFee(Integer id) throws ValidationException {
        return ResponseEntity.ok(feeService.deleteFee(id));
    }


    public ResponseEntity<List<Fee>> getAll()
    {
        return ResponseEntity.ok(feeService.getAll());
    }


    public ResponseEntity<Optional<Fee>> getById(@PathVariable Integer id) throws ValidationException {
        return ResponseEntity.ok(feeService.getById(id));
    }


    public ResponseEntity<List<Fee>> getBySourceCity(@PathVariable String cityName) throws ValidationException
    {
        return ResponseEntity.ok(feeService.getBySourceCity(cityName));
    }


    public ResponseEntity<List<Fee>> getByDestinationCity(@PathVariable String cityName) throws ValidationException
    {
        return ResponseEntity.ok(feeService.getByDestinationCity(cityName));
    }


    public ResponseEntity<FeeRequest> getFeeByIdCities(@PathVariable Integer idCityFrom, @PathVariable Integer idCityTo) throws ValidationException
    {
        return ResponseEntity.ok(feeService.getFeeByIdCities(idCityFrom, idCityTo));
    }


    public ResponseEntity<FeeRequest> getFeeByNameCities(@PathVariable String cityFrom, @PathVariable String cityTo) throws ValidationException
    {
        return ResponseEntity.ok(feeService.getFeeByNameCities(cityFrom, cityTo));
    }

    private URI getUri(Fee fee)
    {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}/")
                .buildAndExpand(fee.getId())
                .toUri();
    }
}
