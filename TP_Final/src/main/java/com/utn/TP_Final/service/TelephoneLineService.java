package com.utn.TP_Final.service;

import com.utn.TP_Final.exceptions.TelephoneLineAlreadyExistsException;
import com.utn.TP_Final.exceptions.TelephoneLineNotExistsException;
import com.utn.TP_Final.model.TelephoneLine;
import com.utn.TP_Final.repository.TelephoneLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class TelephoneLineService {

    private final TelephoneLineRepository telephoneLineRepository;

    @Autowired
    public TelephoneLineService(TelephoneLineRepository telephoneLineRepository) {
        this.telephoneLineRepository = telephoneLineRepository;
    }

    public TelephoneLine addTelephoneLine(TelephoneLine newTelephoneLine) throws TelephoneLineAlreadyExistsException
    {
        TelephoneLine telephoneLine = telephoneLineRepository.save(newTelephoneLine);
        return Optional.ofNullable(telephoneLine).orElseThrow(()-> new TelephoneLineAlreadyExistsException());
    }

    public TelephoneLine deleteTelephoneLine(String lineNumber) throws TelephoneLineNotExistsException
    {
        TelephoneLine telephoneLine = telephoneLineRepository.delete(lineNumber);
        return Optional.ofNullable(telephoneLine).orElseThrow(()-> new TelephoneLineNotExistsException());
    }

    public List<TelephoneLine> getAll(String lineNumber)
    {
        if(isNull(lineNumber))
        {
            return telephoneLineRepository.findAll();
        }
        List<TelephoneLine> telephoneLines = new ArrayList<TelephoneLine>();
        telephoneLines.add(telephoneLineRepository.findByLineNumber(lineNumber));
        return telephoneLines;
    }

    public TelephoneLine suspendTelephoneLine(String lineNumber) throws TelephoneLineNotExistsException
    {
        TelephoneLine telephoneLine = telephoneLineRepository.suspendTelephoneLine(lineNumber);
        return Optional.ofNullable(telephoneLine).orElseThrow(()-> new TelephoneLineNotExistsException());
    }

    public TelephoneLine activeTelephoneLine(String lineNumber) throws TelephoneLineNotExistsException
    {
        TelephoneLine telephoneLine = telephoneLineRepository.activeTelephoneLine(lineNumber);
        return Optional.ofNullable(telephoneLine).orElseThrow(()-> new TelephoneLineNotExistsException());
    }

    public TelephoneLine findByLineNumber(String number) throws TelephoneLineNotExistsException
    {
        TelephoneLine telephoneLine = telephoneLineRepository.findByLineNumber(number);
        return Optional.ofNullable(telephoneLine).orElseThrow(()-> new TelephoneLineNotExistsException());
    }
}
