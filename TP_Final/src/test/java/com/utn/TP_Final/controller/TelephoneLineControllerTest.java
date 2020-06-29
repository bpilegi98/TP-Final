package com.utn.TP_Final.controller;

import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.model.TelephoneLine;
import com.utn.TP_Final.model.enums.LineStatus;
import com.utn.TP_Final.service.TelephoneLineService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class TelephoneLineControllerTest {

    @InjectMocks
    TelephoneLineController telephoneLineController;

    @Mock
    TelephoneLineService telephoneLineService;

    @Mock
    HttpServletRequest request;

    @Before
    public void setUp()
    {
        initMocks(this);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void addTelephoneLineTest() throws ValidationException {
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, null, null);
        when(telephoneLineService.addTelephoneLine(telephoneLine)).thenReturn(telephoneLine);
        ResponseEntity<TelephoneLine> telephoneLineResult = telephoneLineController.addTelephoneLine(telephoneLine);
        assertEquals(HttpStatus.CREATED, telephoneLineResult.getStatusCode());
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
        assertEquals(HttpStatus.OK, telephoneLinesResult.getStatusCode());
    }

    @Test
    public void suspendTelephoneLineOK() throws ValidationException, ValidationException {
        LineStatus lineStatus = LineStatus.ACTIVE;
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, lineStatus, null);
        when(telephoneLineService.suspendTelephoneLine(telephoneLine.getLineNumber())).thenReturn(telephoneLine);
        ResponseEntity<TelephoneLine> telephoneLineResult = telephoneLineController.suspendTelephoneLine(telephoneLine.getLineNumber());
        assertEquals(HttpStatus.OK, telephoneLineResult.getStatusCode());
    }


    @Test
    public void activeTelephoneLineOK() throws ValidationException, ValidationException {
        LineStatus lineStatus = LineStatus.ACTIVE;
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, lineStatus, null);
        when(telephoneLineService.activeTelephoneLine(telephoneLine.getLineNumber())).thenReturn(telephoneLine);
        ResponseEntity<TelephoneLine> telephoneLineResult = telephoneLineController.activeTelephoneLine(telephoneLine.getLineNumber());
        assertEquals(HttpStatus.OK, telephoneLineResult.getStatusCode());
    }


    @Test
    public void getByNumberOk() throws ValidationException, ValidationException {
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, null, null);
        when(telephoneLineService.findByLineNumber(telephoneLine.getLineNumber())).thenReturn(telephoneLine);
        ResponseEntity<TelephoneLine> result = telephoneLineController.getByNumber(telephoneLine.getLineNumber());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(telephoneLineService, times(1)).findByLineNumber(telephoneLine.getLineNumber());
    }
}
