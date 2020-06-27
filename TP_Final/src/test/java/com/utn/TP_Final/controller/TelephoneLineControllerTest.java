package com.utn.TP_Final.controller;

import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.model.TelephoneLine;
import com.utn.TP_Final.model.enums.LineStatus;
import com.utn.TP_Final.service.TelephoneLineService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    public void addTelephoneLineTest() throws ValidationException {
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, null, null);
        when(telephoneLineService.addTelephoneLine(telephoneLine)).thenReturn(telephoneLine);
        ResponseEntity<TelephoneLine> telephoneLineResult = telephoneLineController.addTelephoneLine(telephoneLine);
        assertEquals(HttpStatus.CREATED, telephoneLineResult.getStatusCode());
    }

    @Test(expected = ValidationException.class)
    public void addTelephoneLineAlreadyExists() throws ValidationException {
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, null, null);
        when(telephoneLineService.addTelephoneLine(telephoneLine)).thenReturn(null);
        telephoneLineController.addTelephoneLine(telephoneLine);
    }

    @Test
    public void deleteTelephoneLineOk() throws ValidationException {
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, null, null);
        when(telephoneLineService.deleteTelephoneLine(telephoneLine.getLineNumber())).thenReturn(telephoneLine);
        ResponseEntity<TelephoneLine> telephoneLineResult = telephoneLineController.removeTelephoneLine(telephoneLine.getLineNumber());
        assertEquals(HttpStatus.OK, telephoneLineResult.getStatusCode());
    }

    @Test(expected = ValidationException.class)
    public void deleteTelephoneLineNotExists() throws ValidationException {
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

        ResponseEntity<List<TelephoneLine>> telephoneLineList = telephoneLineController.getAll(null);
        assertEquals(HttpStatus.OK, telephoneLineList.getStatusCode());
        verify(telephoneLineService, times(1)).getAll(null);
    }

    @Test
    public void getAllEmptyTest()
    {
        List<TelephoneLine> telephoneLines = new ArrayList<TelephoneLine>();
        when(telephoneLineService.getAll(null)).thenReturn(telephoneLines);
        ResponseEntity<List<TelephoneLine>> telephoneLinesResult = telephoneLineController.getAll(null);
        assertEquals(HttpStatus.NO_CONTENT, telephoneLinesResult.getStatusCode());
    }

    @Test
    public void suspendTelephoneLineOK() throws ValidationException, ValidationException {
        LineStatus lineStatus = LineStatus.ACTIVE;
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, lineStatus, null);
        when(telephoneLineService.suspendTelephoneLine(telephoneLine.getLineNumber())).thenReturn(telephoneLine);
        ResponseEntity<TelephoneLine> telephoneLineResult = telephoneLineController.suspendTelephoneLine(telephoneLine.getLineNumber());
        assertEquals(HttpStatus.OK, telephoneLineResult.getStatusCode());
    }

    @Test(expected = ValidationException.class)
    public void suspendTelephoneLineNotExists() throws ValidationException, ValidationException {
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, null, null);
        when(telephoneLineService.suspendTelephoneLine(telephoneLine.getLineNumber())).thenReturn(null);
        telephoneLineController.suspendTelephoneLine(telephoneLine.getLineNumber());
    }

    @Test
    public void activeTelephoneLineOK() throws ValidationException, ValidationException {
        LineStatus lineStatus = LineStatus.ACTIVE;
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, lineStatus, null);
        when(telephoneLineService.activeTelephoneLine(telephoneLine.getLineNumber())).thenReturn(telephoneLine);
        ResponseEntity<TelephoneLine> telephoneLineResult = telephoneLineController.activeTelephoneLine(telephoneLine.getLineNumber());
        assertEquals(HttpStatus.OK, telephoneLineResult.getStatusCode());
    }

    @Test(expected = ValidationException.class)
    public void activeTelephoneLineNotExists() throws ValidationException, ValidationException {
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, null, null);
        when(telephoneLineService.activeTelephoneLine(telephoneLine.getLineNumber())).thenReturn(null);
        telephoneLineController.activeTelephoneLine(telephoneLine.getLineNumber());
    }


    @Test
    public void getByNumberOk() throws ValidationException, ValidationException {
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, null, null);
        when(telephoneLineService.findByLineNumber(telephoneLine.getLineNumber())).thenReturn(telephoneLine);
        ResponseEntity<TelephoneLine> result = telephoneLineController.getByNumber(telephoneLine.getLineNumber());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(telephoneLineService, times(1)).findByLineNumber(telephoneLine.getLineNumber());
    }

    @Test(expected = ValidationException.class)
    public void getByNumberTelephoneLineNotExists() throws ValidationException, ValidationException {
        when(telephoneLineService.findByLineNumber("2235388479")).thenReturn(null);
        telephoneLineController.getByNumber("2235388479");
    }
}
