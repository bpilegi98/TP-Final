package com.utn.TP_Final.service;

import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.model.City;
import com.utn.TP_Final.model.TelephoneLine;
import com.utn.TP_Final.model.enums.LineStatus;
import com.utn.TP_Final.repository.TelephoneLineRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class TelephoneLineServiceTest {

    @Autowired
    TelephoneLineService telephoneLineService;

    @Mock
    TelephoneLineRepository telephoneLineRepository;

    @Before
    public void setUp()
    {
        initMocks(this);
        telephoneLineService = new TelephoneLineService(telephoneLineRepository);
    }

    @Test
    public void addTelephoneLineTest() throws ValidationException
    {
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, null, null);
        when(telephoneLineRepository.save(telephoneLine)).thenReturn(telephoneLine);
        TelephoneLine telephoneLineResult = telephoneLineService.addTelephoneLine(telephoneLine);
        assertEquals(telephoneLine.getLineNumber(), telephoneLineResult.getLineNumber());
    }

    @Test(expected = ValidationException.class)
    public void addTelephoneLineAlreadyExists() throws ValidationException
    {
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, null, null);
        when(telephoneLineRepository.save(telephoneLine)).thenReturn(null);
        telephoneLineService.addTelephoneLine(telephoneLine);
    }

    @Test
    public void getAllTest()
    {
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, null, null);
        TelephoneLine telephoneLine2 = new TelephoneLine(2, "2235388478", null, null, null);
        List<TelephoneLine> telephoneLines = new ArrayList<TelephoneLine>();
        telephoneLines.add(telephoneLine);
        telephoneLines.add(telephoneLine2);

        when(telephoneLineRepository.findAll()).thenReturn(telephoneLines);

        List<TelephoneLine> telephoneLineList = telephoneLineService.getAll(null);
        assertEquals(2, telephoneLineList.size());
        verify(telephoneLineRepository, times(1)).findAll();
    }

    @Test
    public void getAllOneCityTest() {
        List<TelephoneLine> telephoneLines = new ArrayList<TelephoneLine>();
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, null, null);
        telephoneLines.add(telephoneLine);
        when(telephoneLineRepository.findByLineNumber("2235388479")).thenReturn(telephoneLine);
        List<TelephoneLine> result = telephoneLineService.getAll("2235388479");
        assertEquals(telephoneLines, result);
    }

    @Test
    public void getAllEmptyTest()
    {
        List<TelephoneLine> telephoneLines = new ArrayList<TelephoneLine>();
        when(telephoneLineRepository.findAll()).thenReturn(telephoneLines);
        List<TelephoneLine> telephoneLinesResult = telephoneLineService.getAll(null);
        assertEquals(telephoneLines, telephoneLinesResult);
    }

    @Test
    public void suspendTelephoneLineOK() throws ValidationException
    {
        LineStatus lineStatus = LineStatus.ACTIVE;
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, lineStatus, null);
        when(telephoneLineRepository.suspendTelephoneLine(telephoneLine.getLineNumber())).thenReturn(1);
        when(telephoneLineRepository.findByLineNumber(telephoneLine.getLineNumber())).thenReturn(new TelephoneLine(1, "2235388479", null, LineStatus.SUSPENDED, null));
        TelephoneLine telephoneLineResult = telephoneLineService.suspendTelephoneLine(telephoneLine.getLineNumber());
        assertEquals(lineStatus.SUSPENDED, telephoneLineResult.getStatus());
    }

    @Test(expected = ValidationException.class)
    public void suspendTelephoneLineNotExists() throws ValidationException
    {
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, null, null);
        when(telephoneLineRepository.findByLineNumber(telephoneLine.getLineNumber())).thenReturn(null);
        telephoneLineService.suspendTelephoneLine(telephoneLine.getLineNumber());
    }

    @Test
    public void activeTelephoneLineOK() throws ValidationException
    {
        LineStatus lineStatus = LineStatus.SUSPENDED;
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, lineStatus, null);
        when(telephoneLineRepository.activeTelephoneLine(telephoneLine.getLineNumber())).thenReturn(1);
        telephoneLine.setStatus(LineStatus.ACTIVE);
        when(telephoneLineRepository.findByLineNumber(telephoneLine.getLineNumber())).thenReturn(telephoneLine);
        TelephoneLine telephoneLineResult = telephoneLineService.activeTelephoneLine(telephoneLine.getLineNumber());
        assertEquals(LineStatus.ACTIVE, telephoneLineResult.getStatus());
    }

    @Test(expected = ValidationException.class)
    public void activeTelephoneLineNotExists() throws ValidationException
    {
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, null, null);
        when(telephoneLineRepository.activeTelephoneLine(telephoneLine.getLineNumber())).thenReturn(0);
        when(telephoneLineRepository.findByLineNumber(telephoneLine.getLineNumber())).thenReturn(null);
        telephoneLineService.activeTelephoneLine(telephoneLine.getLineNumber());
    }


    @Test
    public void getByNumberOk() throws ValidationException
    {
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, null, null);
        when(telephoneLineRepository.findByLineNumber(telephoneLine.getLineNumber())).thenReturn(telephoneLine);
        assertEquals(telephoneLineService.findByLineNumber(telephoneLine.getLineNumber()), telephoneLine);
        verify(telephoneLineRepository, times(1)).findByLineNumber(telephoneLine.getLineNumber());
    }

    @Test(expected = ValidationException.class)
    public void getByNumberTelephoneLineNotExists() throws ValidationException
    {
        when(telephoneLineRepository.findByLineNumber("2235388479")).thenReturn(null);
        telephoneLineService.findByLineNumber("2235388479");
    }
}
