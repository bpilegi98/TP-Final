package com.utn.TP_Final.service;

import com.utn.TP_Final.model.Fee;
import com.utn.TP_Final.repository.FeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Fee> getAll()
    {
        return feeRepository.findAll();
    }
}
