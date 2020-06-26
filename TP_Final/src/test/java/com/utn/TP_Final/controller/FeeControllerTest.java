package com.utn.TP_Final.controller;

import com.utn.TP_Final.exceptions.CityNotExistsException;
import com.utn.TP_Final.exceptions.FeeAlreadyExistsException;
import com.utn.TP_Final.exceptions.FeeNotExistsException;
import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.model.City;
import com.utn.TP_Final.model.Fee;
import com.utn.TP_Final.projections.FeeRequest;
import com.utn.TP_Final.service.FeeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class FeeControllerTest {

    @Autowired
    FeeController feeController;

    @Mock
    FeeService feeService;

    @Before
    public void setUp()
    {
        initMocks(this);
        feeController = new FeeController(feeService);
    }

    @Test
    public void addFeeTest() throws FeeAlreadyExistsException
    {
        Fee fee = new Fee(1, 2, 4, null, null);
        when(feeService.addFee(fee)).thenReturn(fee);
        Fee feeResult = feeController.addFee(fee);
        assertEquals(fee.getCostPerMinute(), feeResult.getCostPerMinute());
    }

    @Test(expected = FeeAlreadyExistsException.class)
    public void addFeeAlreadyExists() throws FeeAlreadyExistsException
    {
        Fee fee = new Fee(1, 2, 4, null, null);
        when(feeService.addFee(fee)).thenReturn(null);
        feeController.addFee(fee);
    }

    @Test
    public void deleteFeeOk() throws FeeNotExistsException
    {
        Fee fee = new Fee(1, 2, 4, null, null);
        when(feeService.deleteFee(1)).thenReturn(fee);
        Fee feeResult = feeController.deleteFee(1);
        assertEquals(fee, feeResult);
    }

    @Test(expected = FeeNotExistsException.class)
    public void deleteFeeNotExists() throws FeeNotExistsException
    {
        when(feeService.deleteFee(1)).thenReturn(null);
        feeController.deleteFee(1);
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
        List<Fee> feeListResult = feeController.getAll();
        assertEquals(2, feeListResult.size());
        verify(feeService, times(1)).getAll();
    }

    @Test
    public void getAllEmptyTest()
    {
        List<Fee> fees = new ArrayList<Fee>();
        when(feeService.getAll()).thenReturn(fees);
        List<Fee> feeResult = feeController.getAll();
        assertEquals(fees, feeResult);
    }

    @Test
    public void getByIdOk() throws FeeNotExistsException
    {
        Fee fee = new Fee(1, 10, 5, null, null);
        Fee fee2 = new Fee(2, 4, 2, null, null);
        List<Fee> feeList = new ArrayList<Fee>();
        feeList.add(fee);
        feeList.add(fee2);
        Optional<Fee> optionalFee = Optional.ofNullable(feeList.get(0));
        when(feeService.getById(1)).thenReturn(optionalFee);
        Optional<Fee> feeResult = feeController.getById(1);
        assertEquals(optionalFee, feeResult);
        verify(feeService, times(1)).getById(1);
    }

    @Test(expected = FeeNotExistsException.class)
    public void getByIdFeeNotExists() throws FeeNotExistsException
    {
        when(feeService.getById(1)).thenReturn(null);
        feeController.getById(1);
    }

    @Test
    public void getBySourceCityOk() throws FeeNotExistsException, CityNotExistsException, ValidationException {
        City city = new City(1, "Mar del Plata", "223", null);
        Fee fee = new Fee(1, 10, 5, city, null);
        Fee fee2 = new Fee(2, 4, 2, null, null);
        List<Fee> feeList = new ArrayList<Fee>();
        feeList.add(fee);
        feeList.add(fee2);
        List<Fee> feeCast = new ArrayList<Fee>();
        feeCast.add( feeList.get(0));
        when(feeService.getBySourceCity("Mar del Plata")).thenReturn(feeCast);
        List<Fee> feeResult = feeController.getBySourceCity("Mar del Plata");
        assertEquals(feeCast, feeResult);
        verify(feeService, times(1)).getBySourceCity("Mar del Plata");
    }

    @Test(expected = FeeNotExistsException.class)
    public void getBySourceCityFeeNotExists() throws FeeNotExistsException, CityNotExistsException, ValidationException {
        when(feeService.getBySourceCity("Mar del Plata")).thenReturn(null);
        feeController.getBySourceCity("Mar del Plata");
    }

    @Test
    public void getByDestinationCityOk() throws FeeNotExistsException, CityNotExistsException, ValidationException {
        City city = new City(1, "Mar del Plata", "223", null);
        Fee fee = new Fee(1, 10, 5, null, city);
        Fee fee2 = new Fee(2, 4, 2, null, null);
        List<Fee> feeList = new ArrayList<Fee>();
        feeList.add(fee);
        feeList.add(fee2);
        List<Fee> feeCast = new ArrayList<Fee>();
        feeCast.add( feeList.get(0));
        when(feeService.getByDestinationCity("Mar del Plata")).thenReturn(feeCast);
        List<Fee> feeResult = feeController.getByDestinationCity("Mar del Plata");
        assertEquals(feeCast, feeResult);
        verify(feeService, times(1)).getByDestinationCity("Mar del Plata");
    }

    @Test(expected = FeeNotExistsException.class)
    public void getByDestinationCityFeeNotExists() throws FeeNotExistsException, CityNotExistsException, ValidationException {
        when(feeService.getByDestinationCity("Mar del Plata")).thenReturn(null);
        feeController.getByDestinationCity("Mar del Plata");
    }

    @Test
    public void getByIdCitiesOk() throws FeeNotExistsException, CityNotExistsException, ValidationException {
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
        assertEquals(feeController.getFeeByIdCities(1,1), feeRequest);
        verify(feeService, times(1)).getFeeByIdCities(1,1);
    }

    @Test(expected = FeeNotExistsException.class)
    public void getByIdCitiesFeeNotExists() throws FeeNotExistsException, CityNotExistsException, ValidationException {
        when(feeService.getFeeByIdCities(1,1)).thenReturn(null);
        feeController.getFeeByIdCities(1,1);
    }

    @Test
    public void getByNameCitiesOk() throws FeeNotExistsException, CityNotExistsException, ValidationException {
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
        assertEquals(feeController.getFeeByNameCities("Mar del Plata", "Mar del Plata"), feeRequest);
        verify(feeService, times(1)).getFeeByNameCities("Mar del Plata", "Mar del Plata");
    }

    @Test(expected = FeeNotExistsException.class)
    public void getByNameCitiesFeeNotExists() throws FeeNotExistsException, CityNotExistsException, ValidationException {
        when(feeService.getFeeByNameCities("Mar del Plata", "Mar del Plata")).thenReturn(null);
        feeController.getFeeByNameCities("Mar del Plata", "Mar del Plata");
    }
}
