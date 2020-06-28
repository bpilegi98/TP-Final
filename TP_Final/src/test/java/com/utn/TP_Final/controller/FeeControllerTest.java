package com.utn.TP_Final.controller;

import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.model.City;
import com.utn.TP_Final.model.Fee;
import com.utn.TP_Final.projections.FeeRequest;
import com.utn.TP_Final.service.FeeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class FeeControllerTest {

    @InjectMocks
    FeeController feeController;

    @Mock
    FeeService feeService;

    @Mock
    HttpServletRequest request;

    @Before
    public void setUp()
    {
        initMocks(this);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void addFeeTest() throws ValidationException
    {
        Fee fee = new Fee(1, 2, 4, null, null);
        when(feeService.addFee(fee)).thenReturn(fee);
        ResponseEntity<Fee> feeResult = feeController.addFee(fee);
        assertEquals(HttpStatus.CREATED, feeResult.getStatusCode());
    }

    @Test
    public void deleteFeeOk() throws ValidationException
    {
        Fee fee = new Fee(1, 2, 4, null, null);
        when(feeService.deleteFee(1)).thenReturn(fee);
        ResponseEntity<Fee> feeResult = feeController.deleteFee(1);
        assertEquals(HttpStatus.OK, feeResult.getStatusCode());
    }

    @Test
    public void getAllTest()
    {
        Fee fee = new Fee(1, 2, 4, null, null);
        Fee fee2 = new Fee(2, 2, 4, null, null);
        List<Fee> feeList = new ArrayList<Fee>();
        feeList.add(fee);
        feeList.add(fee2);
        when(feeService.getAll()).thenReturn(feeList);
        ResponseEntity<List<Fee>> feeListResult = feeController.getAll();
        assertEquals(HttpStatus.OK, feeListResult.getStatusCode());
        verify(feeService, times(1)).getAll();
    }

    @Test
    public void getAllEmptyTest()
    {
        List<Fee> fees = new ArrayList<Fee>();
        when(feeService.getAll()).thenReturn(fees);
        ResponseEntity<List<Fee>> feeResult = feeController.getAll();
        assertEquals(HttpStatus.OK, feeResult.getStatusCode());
    }

    @Test
    public void getByIdOk() throws ValidationException
    {
        Fee fee = new Fee(1, 10, 5, null, null);
        Fee fee2 = new Fee(2, 4, 2, null, null);
        List<Fee> feeList = new ArrayList<Fee>();
        feeList.add(fee);
        feeList.add(fee2);
        Optional<Fee> optionalFee = Optional.ofNullable(feeList.get(0));
        when(feeService.getById(1)).thenReturn(optionalFee);
        ResponseEntity<Optional<Fee>> feeResult = feeController.getById(1);
        assertEquals(HttpStatus.OK, feeResult.getStatusCode());
        verify(feeService, times(1)).getById(1);
    }

    @Test
    public void getBySourceCityOk() throws ValidationException {
        City city = new City(1, "Mar del Plata", "223", null);
        Fee fee = new Fee(1, 10, 5, city, null);
        Fee fee2 = new Fee(2, 4, 2, null, null);
        List<Fee> feeList = new ArrayList<Fee>();
        feeList.add(fee);
        feeList.add(fee2);
        List<Fee> feeCast = new ArrayList<Fee>();
        feeCast.add( feeList.get(0));
        when(feeService.getBySourceCity("Mar del Plata")).thenReturn(feeCast);
        ResponseEntity<List<Fee>> feeResult = feeController.getBySourceCity("Mar del Plata");
        assertEquals(HttpStatus.OK, feeResult.getStatusCode());
        verify(feeService, times(1)).getBySourceCity("Mar del Plata");
    }

    @Test
    public void getByDestinationCityOk() throws ValidationException {
        City city = new City(1, "Mar del Plata", "223", null);
        Fee fee = new Fee(1, 10, 5, null, city);
        Fee fee2 = new Fee(2, 4, 2, null, null);
        List<Fee> feeList = new ArrayList<Fee>();
        feeList.add(fee);
        feeList.add(fee2);
        List<Fee> feeCast = new ArrayList<Fee>();
        feeCast.add( feeList.get(0));
        when(feeService.getByDestinationCity("Mar del Plata")).thenReturn(feeCast);
        ResponseEntity<List<Fee>> feeResult = feeController.getByDestinationCity("Mar del Plata");
        assertEquals(HttpStatus.OK, feeResult.getStatusCode());
        verify(feeService, times(1)).getByDestinationCity("Mar del Plata");
    }

    @Test
    public void getByIdCitiesOk() throws ValidationException {
        City city = new City(1, "Mar del Plata", "223", null);
        Fee fee = new Fee(1, 10, 5, city, city);
        Fee fee2 = new Fee(2, 4, 2, null, null);
        List<Fee> feeList = new ArrayList<Fee>();
        feeList.add(fee);
        feeList.add(fee2);

        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        FeeRequest feeRequest = factory.createProjection(FeeRequest.class);
        feeRequest.setCityFrom("Mar del Plata");
        feeRequest.setCityTo("Mar del Plata");
        feeRequest.setFee(10);

        when(feeService.getFeeByIdCities(1,1)).thenReturn(feeRequest);
        ResponseEntity<FeeRequest> result = feeController.getFeeByIdCities(1,1);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(feeService, times(1)).getFeeByIdCities(1,1);
    }

    @Test
    public void getByNameCitiesOk() throws ValidationException {
        City city = new City(1, "Mar del Plata", "223", null);
        Fee fee = new Fee(1, 10, 5, city, city);
        Fee fee2 = new Fee(2, 4, 2, null, null);
        List<Fee> feeList = new ArrayList<Fee>();
        feeList.add(fee);
        feeList.add(fee2);

        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        FeeRequest feeRequest = factory.createProjection(FeeRequest.class);
        feeRequest.setCityFrom("Mar del Plata");
        feeRequest.setCityTo("Mar del Plata");
        feeRequest.setFee(10);

        when(feeService.getFeeByNameCities("Mar del Plata", "Mar del Plata")).thenReturn(feeRequest);
        ResponseEntity<FeeRequest> result = feeController.getFeeByNameCities("Mar del Plata", "Mar del Plata");
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(feeService, times(1)).getFeeByNameCities("Mar del Plata", "Mar del Plata");
    }
}
