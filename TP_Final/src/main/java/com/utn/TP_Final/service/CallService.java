package com.utn.TP_Final.service;


import com.utn.TP_Final.model.Call;
import com.utn.TP_Final.repository.CallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CallService {

    private final CallRepository callRepository;

    @Autowired
    public CallService(CallRepository callRepository) {
        this.callRepository = callRepository;
    }

    public void addCall(Call newCall)
    {
        callRepository.save(newCall);
    }

    public List<Call> getAll()
    {
        return callRepository.findAll();
    }
}
