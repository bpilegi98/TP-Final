package com.utn.TP_Final.service;

import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.model.Fee;
import com.utn.TP_Final.projections.FeeRequest;
import com.utn.TP_Final.repository.FeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeeService {

    private final FeeRepository feeRepository;

    @Autowired
    public FeeService(FeeRepository feeRepository) {
        this.feeRepository = feeRepository;
    }

    public Fee addFee(Fee newFee) throws ValidationException
    {
        Fee fee = feeRepository.save(newFee);
        return Optional.ofNullable(fee).orElseThrow(()-> new ValidationException("That fee already exists."));
    }

    public Fee deleteFee(Integer id) throws ValidationException
    {
        Fee fee = feeRepository.delete(id);
        return Optional.ofNullable(fee).orElseThrow(()-> new ValidationException("Couldn't delete, that fee doesn't exists."));
    }

    public List<Fee> getAll()
    {
        return feeRepository.findAll();
    }

    public Optional<Fee> getById(Integer id) throws ValidationException
    {
        Optional<Fee> fee = feeRepository.findById(id);
        return Optional.ofNullable(fee).orElseThrow(()-> new ValidationException("Couldn't find that fee."));
    }

    public List<Fee> getBySourceCity(String cityName) throws ValidationException
    {
        List<Fee> fees = feeRepository.findBySourceCity(cityName);
        return Optional.ofNullable(fees).orElseThrow(()-> new ValidationException("Couldn't find that fee."));
    }

    public List<Fee> getByDestinationCity(String cityName) throws ValidationException
    {
        List<Fee> fees = feeRepository.findByDestinationCity(cityName);
        return Optional.ofNullable(fees).orElseThrow(()-> new ValidationException("Couldn't find that fee."));
    }

    public FeeRequest getFeeByIdCities(Integer idCityFrom, Integer idCityTo) throws ValidationException
    {
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        FeeRequest feeRequest = factory.createProjection(FeeRequest.class);
        feeRequest = feeRepository.getFeeByIdCities(idCityFrom, idCityTo);
        return Optional.ofNullable(feeRequest).orElseThrow(()-> new ValidationException("Couldn't find that fee."));
    }

    public FeeRequest getFeeByNameCities(String cityFrom, String cityTo) throws ValidationException
    {
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        FeeRequest feeRequest = factory.createProjection(FeeRequest.class);
        feeRequest = feeRepository.getFeeByNameCities(cityFrom, cityTo);
        return Optional.ofNullable(feeRequest).orElseThrow(()-> new ValidationException("Couldn't find that fee."));
    }
}
