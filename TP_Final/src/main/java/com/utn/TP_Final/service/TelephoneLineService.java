package com.utn.TP_Final.service;

import com.utn.TP_Final.exceptions.ValidationException;
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
    public TelephoneLineService(TelephoneLineRepository telephoneLineRepository)
    {
        this.telephoneLineRepository = telephoneLineRepository;
    }

    public TelephoneLine addTelephoneLine(TelephoneLine newTelephoneLine) throws ValidationException
    {
        TelephoneLine telephoneLine = telephoneLineRepository.save(newTelephoneLine);
        return Optional.ofNullable(telephoneLine).orElseThrow(()-> new ValidationException("That telephone line already exists."));
    }

    public TelephoneLine deleteTelephoneLine(String lineNumber) throws ValidationException
    {
        TelephoneLine telephoneLine = telephoneLineRepository.delete(lineNumber);
        return Optional.ofNullable(telephoneLine).orElseThrow(()-> new ValidationException("Couldn't delete, that telephone line doesn't exists."));
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

    public TelephoneLine suspendTelephoneLine(String lineNumber) throws ValidationException
    {
        TelephoneLine telephoneLine = telephoneLineRepository.suspendTelephoneLine(lineNumber);
        return Optional.ofNullable(telephoneLine).orElseThrow(()-> new ValidationException("Couldn't suspend, that telephone line doesn't exists."));
    }

    public TelephoneLine activeTelephoneLine(String lineNumber) throws ValidationException
    {
        TelephoneLine telephoneLine = telephoneLineRepository.activeTelephoneLine(lineNumber);
        return Optional.ofNullable(telephoneLine).orElseThrow(()-> new ValidationException("Couldn't active, that telephone line doesn't exists."));
    }

    public TelephoneLine findByLineNumber(String number) throws ValidationException
    {
        TelephoneLine telephoneLine = telephoneLineRepository.findByLineNumber(number);
        return Optional.ofNullable(telephoneLine).orElseThrow(()-> new ValidationException("Couldn't find that telephone line."));
    }
}
