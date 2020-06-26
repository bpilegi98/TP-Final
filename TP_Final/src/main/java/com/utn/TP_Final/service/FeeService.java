package com.utn.TP_Final.service;

import com.utn.TP_Final.exceptions.FeeAlreadyExistsException;
import com.utn.TP_Final.exceptions.FeeNotExistsException;
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

    public Fee addFee(Fee newFee) throws FeeAlreadyExistsException
    {
        Fee fee = feeRepository.save(newFee);
        return Optional.ofNullable(fee).orElseThrow(()-> new FeeAlreadyExistsException());
    }

    public Fee deleteFee(Integer id) throws FeeNotExistsException
    {
        Fee fee = feeRepository.delete(id);
        return Optional.ofNullable(fee).orElseThrow(()-> new FeeNotExistsException());
    }

    public List<Fee> getAll()
    {
        return feeRepository.findAll();
    }

    public Optional<Fee> getById(Integer id) throws FeeNotExistsException
    {
        Optional<Fee> fee = feeRepository.findById(id);
        return Optional.ofNullable(fee).orElseThrow(()-> new FeeNotExistsException());
    }

    public List<Fee> getBySourceCity(String cityName) throws FeeNotExistsException
    {
        List<Fee> fees = feeRepository.findBySourceCity(cityName);
        return Optional.ofNullable(fees).orElseThrow(()-> new FeeNotExistsException());
    }

    public List<Fee> getByDestinationCity(String cityName) throws FeeNotExistsException
    {
        List<Fee> fees = feeRepository.findByDestinationCity(cityName);
        return Optional.ofNullable(fees).orElseThrow(()-> new FeeNotExistsException());
    }

    public FeeRequest getFeeByIdCities(Integer idCityFrom, Integer idCityTo) throws FeeNotExistsException
    {
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        FeeRequest feeRequest = factory.createProjection(FeeRequest.class);
        feeRequest = feeRepository.getFeeByIdCities(idCityFrom, idCityTo);
        return Optional.ofNullable(feeRequest).orElseThrow(()-> new FeeNotExistsException());
    }

    public FeeRequest getFeeByNameCities(String cityFrom, String cityTo) throws FeeNotExistsException
    {
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        FeeRequest feeRequest = factory.createProjection(FeeRequest.class);
        feeRequest = feeRepository.getFeeByNameCities(cityFrom, cityTo);
        return Optional.ofNullable(feeRequest).orElseThrow(()-> new FeeNotExistsException());
    }
}
