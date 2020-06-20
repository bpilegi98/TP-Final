package com.utn.TP_Final.service;


import com.utn.TP_Final.model.Call;
import com.utn.TP_Final.projections.CallsFromUser;
import com.utn.TP_Final.projections.CallsFromUserSimple;
import com.utn.TP_Final.repository.CallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void deleteCall(Call call)
    {
        callRepository.delete(call);
    }

    public List<Call> getAll()
    {
        return callRepository.findAll();
    }

    public Optional<Call> getById(Integer id)
    {
        return callRepository.findById(id);
    }

    public CallsFromUserSimple getCallsFromUserSimple(String dni)
    {
        return callRepository.getCallsFromUserSimple(dni);
    }

    public List<CallsFromUser> getCallsFromUser(String dni)
    {
        return callRepository.getCallsFromUser(dni);
    }
}
