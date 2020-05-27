package com.utn.TP_Final.service;

import com.utn.TP_Final.model.TelephoneLine;
import com.utn.TP_Final.repository.TelephoneLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class TelephoneLineService {

    private final TelephoneLineRepository telephoneLineRepository;

    @Autowired
    public TelephoneLineService(TelephoneLineRepository telephoneLineRepository) {
        this.telephoneLineRepository = telephoneLineRepository;
    }

    public void addTelephoneLine(TelephoneLine newTelephoneLine){
        telephoneLineRepository.save(newTelephoneLine);
    }

    public void deleteTelephoneLine(String lineNumber)
    {
        telephoneLineRepository.delete(lineNumber);
    }

    public List<TelephoneLine> getAll(String lineNumber)
    {
        if(isNull(lineNumber))
        {
            return telephoneLineRepository.findAll();
        }
        return telephoneLineRepository.findByLineNumber(lineNumber);
    }

    public void suspendTelephoneLine(String lineNumber)
    {
        telephoneLineRepository.suspendTelephoneLine(lineNumber);
    }

    public void activeTelephoneLine(String lineNumber)
    {
        telephoneLineRepository.activeTelephoneLine(lineNumber);
    }
}
