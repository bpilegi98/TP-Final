package com.utn.TP_Final.service;

import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.model.City;
import com.utn.TP_Final.model.Fee;
import com.utn.TP_Final.projections.FeeRequest;
import com.utn.TP_Final.repository.FeeRepository;
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

public class FeeServiceTest {

    @Autowired
    FeeService feeService;

    @Mock
    FeeRepository feeRepository;

    @Before
    public void setUp()
    {
        initMocks(this);
        feeService = new FeeService(feeRepository);
    }

    @Test
    public void addFeeTest() throws ValidationException
    {
        Fee fee = new Fee(1, 2, 4, null, null);
        when(feeRepository.save(fee)).thenReturn(fee);
        Fee feeResult = feeService.addFee(fee);
        assertEquals(fee.getCostPerMinute(), feeResult.getCostPerMinute());
    }

    @Test(expected = ValidationException.class)
    public void addFeeAlreadyExists() throws ValidationException
    {
        Fee fee = new Fee(1, 2, 4, null, null);
        when(feeRepository.save(fee)).thenReturn(null);
        feeService.addFee(fee);
    }

    @Test
    public void deleteFeeOk() throws ValidationException
    {
        Fee fee = new Fee(1, 2, 4, null, null);
        when(feeRepository.delete(1)).thenReturn(fee);
        Fee feeResult = feeService.deleteFee(1);
        assertEquals(fee, feeResult);
    }

    @Test(expected = ValidationException.class)
    public void deleteFeeNotExists() throws ValidationException
    {
        when(feeRepository.delete(1)).thenReturn(null);
        feeService.deleteFee(1);
    }

    @Test
    public void getAllTest()
    {
        Fee fee = new Fee(1, 2, 4, null, null);
        Fee fee2 = new Fee(2, 2, 4, null, null);
        List<Fee> feeList = new ArrayList<Fee>();
        feeList.add(fee);
        feeList.add(fee2);
        when(feeRepository.findAll()).thenReturn(feeList);
        List<Fee> feeListResult = feeService.getAll();
        assertEquals(2, feeListResult.size());
        verify(feeRepository, times(1)).findAll();
    }

    @Test
    public void getAllEmptyTest()
    {
        List<Fee> fees = new ArrayList<Fee>();
        when(feeRepository.findAll()).thenReturn(fees);
        List<Fee> feeResult = feeService.getAll();
        assertEquals(fees, feeResult);
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
        when(feeRepository.findById(1)).thenReturn(optionalFee);
        Optional<Fee> feeResult = feeService.getById(1);
        assertEquals(optionalFee, feeResult);
        verify(feeRepository, times(1)).findById(1);
    }

    @Test(expected = ValidationException.class)
    public void getByIdFeeNotExists() throws ValidationException
    {
        when(feeRepository.findById(1)).thenReturn(null);
        feeService.getById(1);
    }

    @Test
    public void getBySourceCityOk() throws ValidationException
    {
        City city = new City(1, "Mar del Plata", "223", null);
        Fee fee = new Fee(1, 10, 5, city, null);
        Fee fee2 = new Fee(2, 4, 2, null, null);
        List<Fee> feeList = new ArrayList<Fee>();
        feeList.add(fee);
        feeList.add(fee2);
        List<Fee> feeCast = new ArrayList<Fee>();
        feeCast.add( feeList.get(0));
        when(feeRepository.findBySourceCity("Mar del Plata")).thenReturn(feeCast);
        List<Fee> feeResult = feeService.getBySourceCity("Mar del Plata");
        assertEquals(feeCast, feeResult);
        verify(feeRepository, times(1)).findBySourceCity("Mar del Plata");
    }

    @Test(expected = ValidationException.class)
    public void getBySourceCityFeeNotExists() throws ValidationException
    {
        when(feeRepository.findBySourceCity("Mar del Plata")).thenReturn(null);
        feeService.getBySourceCity("Mar del Plata");
    }

    @Test
    public void getByDestinationCityOk() throws ValidationException
    {
        City city = new City(1, "Mar del Plata", "223", null);
        Fee fee = new Fee(1, 10, 5, null, city);
        Fee fee2 = new Fee(2, 4, 2, null, null);
        List<Fee> feeList = new ArrayList<Fee>();
        feeList.add(fee);
        feeList.add(fee2);
        List<Fee> feeCast = new ArrayList<Fee>();
        feeCast.add( feeList.get(0));
        when(feeRepository.findByDestinationCity("Mar del Plata")).thenReturn(feeCast);
        List<Fee> feeResult = feeService.getByDestinationCity("Mar del Plata");
        assertEquals(feeCast, feeResult);
        verify(feeRepository, times(1)).findByDestinationCity("Mar del Plata");
    }

    @Test(expected = ValidationException.class)
    public void getByDestinationCityFeeNotExists() throws ValidationException
    {
        when(feeRepository.findByDestinationCity("Mar del Plata")).thenReturn(null);
        feeService.getByDestinationCity("Mar del Plata");
    }

    @Test
    public void getByIdCitiesOk() throws ValidationException
    {
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

        when(feeRepository.getFeeByIdCities(1,1)).thenReturn(feeRequest);
        assertEquals(feeService.getFeeByIdCities(1,1), feeRequest);
        verify(feeRepository, times(1)).getFeeByIdCities(1,1);
    }

    @Test(expected = ValidationException.class)
    public void getByIdCitiesFeeNotExists() throws ValidationException
    {
        when(feeRepository.getFeeByIdCities(1,1)).thenReturn(null);
        feeService.getFeeByIdCities(1,1);
    }

    @Test
    public void getByNameCitiesOk() throws ValidationException
    {
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

        when(feeRepository.getFeeByNameCities("Mar del Plata", "Mar del Plata")).thenReturn(feeRequest);
        assertEquals(feeService.getFeeByNameCities("Mar del Plata", "Mar del Plata"), feeRequest);
        verify(feeRepository, times(1)).getFeeByNameCities("Mar del Plata", "Mar del Plata");
    }

    @Test(expected = ValidationException.class)
    public void getByNameCitiesFeeNotExists() throws ValidationException
    {
        when(feeRepository.getFeeByNameCities("Mar del Plata", "Mar del Plata")).thenReturn(null);
        feeService.getFeeByNameCities("Mar del Plata", "Mar del Plata");
    }
}
