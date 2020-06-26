package com.utn.TP_Final.controller;

import com.utn.TP_Final.exceptions.TelephoneLineAlreadyExistsException;
import com.utn.TP_Final.exceptions.TelephoneLineNotExistsException;
import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.model.TelephoneLine;
import com.utn.TP_Final.model.enums.LineStatus;
import com.utn.TP_Final.service.TelephoneLineService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class TelephoneLineControllerTest {

    @Autowired
    TelephoneLineController telephoneLineController;

    @Mock
    TelephoneLineService telephoneLineService;

    @Before
    public void setUp()
    {
        initMocks(this);
        telephoneLineController = new TelephoneLineController(telephoneLineService);
    }

    @Test
    public void addTelephoneLineTest() throws TelephoneLineAlreadyExistsException, ValidationException {
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, null, null);
        when(telephoneLineService.addTelephoneLine(telephoneLine)).thenReturn(telephoneLine);
        TelephoneLine telephoneLineResult = telephoneLineController.addTelephoneLine(telephoneLine);
        assertEquals(telephoneLine.getLineNumber(), telephoneLineResult.getLineNumber());
    }

    @Test(expected = TelephoneLineAlreadyExistsException.class)
    public void addTelephoneLineAlreadyExists() throws TelephoneLineAlreadyExistsException, ValidationException {
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, null, null);
        when(telephoneLineService.addTelephoneLine(telephoneLine)).thenReturn(null);
        telephoneLineController.addTelephoneLine(telephoneLine);
    }

    @Test
    public void deleteTelephoneLineOk() throws TelephoneLineNotExistsException, ValidationException {
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, null, null);
        when(telephoneLineService.deleteTelephoneLine(telephoneLine.getLineNumber())).thenReturn(telephoneLine);
        TelephoneLine telephoneLineResult = telephoneLineController.removeTelephoneLine(telephoneLine.getLineNumber());
        assertEquals(telephoneLine, telephoneLineResult);
    }

    @Test(expected = TelephoneLineNotExistsException.class)
    public void deleteTelephoneLineNotExists() throws TelephoneLineNotExistsException, ValidationException {
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, null, null);
        when(telephoneLineService.deleteTelephoneLine(telephoneLine.getLineNumber())).thenReturn(null);
        telephoneLineController.removeTelephoneLine(telephoneLine.getLineNumber());
    }

    @Test
    public void getAllTest()
    {
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, null, null);
        TelephoneLine telephoneLine2 = new TelephoneLine(2, "2235388478", null, null, null);
        List<TelephoneLine> telephoneLines = new ArrayList<TelephoneLine>();
        telephoneLines.add(telephoneLine);
        telephoneLines.add(telephoneLine2);

        when(telephoneLineService.getAll(null)).thenReturn(telephoneLines);

        List<TelephoneLine> telephoneLineList = telephoneLineController.getAll(null);
        assertEquals(2, telephoneLineList.size());
        verify(telephoneLineService, times(1)).getAll(null);
    }

    @Test
    public void getAllEmptyTest()
    {
        List<TelephoneLine> telephoneLines = new ArrayList<TelephoneLine>();
        when(telephoneLineService.getAll(null)).thenReturn(telephoneLines);
        List<TelephoneLine> telephoneLinesResult = telephoneLineController.getAll(null);
        assertEquals(telephoneLines, telephoneLinesResult);
    }

    @Test
    public void suspendTelephoneLineOK() throws TelephoneLineNotExistsException, ValidationException {
        LineStatus lineStatus = LineStatus.ACTIVE;
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, lineStatus, null);
        when(telephoneLineService.suspendTelephoneLine(telephoneLine.getLineNumber())).thenReturn(telephoneLine);
        TelephoneLine telephoneLineResult = telephoneLineController.suspendTelephoneLine(telephoneLine.getLineNumber());
        assertEquals(telephoneLine.getStatus(), telephoneLineResult.getStatus());
    }

    @Test(expected = TelephoneLineNotExistsException.class)
    public void suspendTelephoneLineNotExists() throws TelephoneLineNotExistsException, ValidationException {
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, null, null);
        when(telephoneLineService.suspendTelephoneLine(telephoneLine.getLineNumber())).thenReturn(null);
        telephoneLineController.suspendTelephoneLine(telephoneLine.getLineNumber());
    }

    @Test
    public void activeTelephoneLineOK() throws TelephoneLineNotExistsException, ValidationException {
        LineStatus lineStatus = LineStatus.ACTIVE;
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, lineStatus, null);
        when(telephoneLineService.activeTelephoneLine(telephoneLine.getLineNumber())).thenReturn(telephoneLine);
        TelephoneLine telephoneLineResult = telephoneLineController.activeTelephoneLine(telephoneLine.getLineNumber());
        assertEquals(telephoneLine.getStatus(), telephoneLineResult.getStatus());
    }

    @Test(expected = TelephoneLineNotExistsException.class)
    public void activeTelephoneLineNotExists() throws TelephoneLineNotExistsException, ValidationException {
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, null, null);
        when(telephoneLineService.activeTelephoneLine(telephoneLine.getLineNumber())).thenReturn(null);
        telephoneLineController.activeTelephoneLine(telephoneLine.getLineNumber());
    }


    @Test
    public void getByNumberOk() throws TelephoneLineNotExistsException, ValidationException {
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, null, null);
        when(telephoneLineService.findByLineNumber(telephoneLine.getLineNumber())).thenReturn(telephoneLine);
        assertEquals(telephoneLineController.getByNumber(telephoneLine.getLineNumber()), telephoneLine);
        verify(telephoneLineService, times(1)).findByLineNumber(telephoneLine.getLineNumber());
    }

    @Test(expected = TelephoneLineNotExistsException.class)
    public void getByNumberTelephoneLineNotExists() throws TelephoneLineNotExistsException, ValidationException {
        when(telephoneLineService.findByLineNumber("2235388479")).thenReturn(null);
        telephoneLineController.getByNumber("2235388479");
    }
}
