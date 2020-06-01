package com.utn.TP_Final.service;

import com.utn.TP_Final.model.Fee;
import com.utn.TP_Final.repository.FeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void addFee(Fee newFee)
    {
        feeRepository.save(newFee);
    }

    public void deleteFee(Fee fee)
    {
        feeRepository.delete(fee);
    }

    public List<Fee> getAll()
    {
        return feeRepository.findAll();
    }

    public Optional<Fee> getById(Integer id)
    {
        return feeRepository.findById(id);
    }

    public List<Fee> getBySourceCity(String cityName)
    {
        return feeRepository.findBySourceCity(cityName);
    }

    public List<Fee> getByDestinationCity(String cityName)
    {
        return feeRepository.findByDestinationCity(cityName);
    }
/*
    public Fee getByPrefix(Integer prefixNumber)
    {
        return feeRepository.findByPrefix(prefixNumber);
    }

 */
}
