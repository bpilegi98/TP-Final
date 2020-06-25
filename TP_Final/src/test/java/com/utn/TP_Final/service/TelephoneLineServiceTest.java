package com.utn.TP_Final.service;

import com.utn.TP_Final.exceptions.TelephoneLineAlreadyExistsException;
import com.utn.TP_Final.exceptions.TelephoneLineNotExistsException;
import com.utn.TP_Final.model.TelephoneLine;
import com.utn.TP_Final.model.enums.LineStatus;
import com.utn.TP_Final.repository.TelephoneLineRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
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
/*
    @Test
    public void suspendTelephoneLineOK() throws TelephoneLineNotExistsException
    {
        LineStatus lineStatus = LineStatus.ACTIVE;
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, lineStatus, null);

        when(telephoneLineRepository.suspendTelephoneLine("2235388479")).thenReturn(telephoneLine.setStatus(LineStatus.SUSPENDED));

        TelephoneLine telephoneLineResult = telephoneLineService.suspendTelephoneLine("2235388479");

    }
 */

    @Test
    public void getByNumberOk() throws TelephoneLineNotExistsException
    {
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, null, null);

        //por que castea esto???
        when(telephoneLineRepository.findByLineNumber("2235388479")).thenReturn((List<TelephoneLine>) telephoneLine);
        assertEquals(telephoneLineService.findByNumber("2235388479"), telephoneLine);
        verify(telephoneLineRepository, times(1)).findByLineNumber("2235388479");
    }

    @Test(expected = TelephoneLineAlreadyExistsException.class)
    public void getByNumberTelephoneLineNotExists() throws TelephoneLineNotExistsException
    {
        when(telephoneLineRepository.findByLineNumber("2235388479")).thenReturn(null);
        telephoneLineService.findByNumber("2235388479");
    }
}
