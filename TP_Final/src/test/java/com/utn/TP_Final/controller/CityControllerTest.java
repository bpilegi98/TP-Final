package com.utn.TP_Final.controller;

import com.utn.TP_Final.exceptions.CityAlreadyExistsException;
import com.utn.TP_Final.exceptions.CityNotExistsException;
import com.utn.TP_Final.model.City;
import com.utn.TP_Final.service.CityService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CityControllerTest {

    @Autowired
    CityController cityController;

    @Mock
    CityService cityService;

    @Before
    public void setUp()
    {
        initMocks(this);
        cityController = new CityController(cityService);
    }

    @Test
    public void addCityTest() throws CityAlreadyExistsException
    {
        City city = new City(1, "Mar del Plata", "223", null);
        when(cityService.addCity(city)).thenReturn(city);
        City cityResult = cityController.addCity(city);
        assertEquals(city.getName(), cityResult.getName());
        assertEquals(city.getPrefixNumber(), cityResult.getPrefixNumber());
    }

    @Test(expected = CityAlreadyExistsException.class)
    public void addCityAlreadyExists() throws CityAlreadyExistsException
    {
        City city = new City(1, "Mar del Plata", "223", null);
        when(cityService.addCity(city)).thenReturn(null);
        cityController.addCity(city);
    }

    @Test
    public void deleteCityOk() throws CityNotExistsException
    {
        City city = new City(1, "Mar del Plata", "223", null);
        when(cityService.deleteCity(1)).thenReturn(city);
        City cityResult = cityController.deleteCity(1);
        assertEquals(city, cityResult);
    }

    @Test(expected = CityNotExistsException.class)
    public void deleteCityNotExists() throws CityNotExistsException
    {
        when(cityService.deleteCity(1)).thenReturn(null);
        cityController.deleteCity(1);
    }

    @Test
    public void getAllTest()
    {
        List<City> cities = new ArrayList<City>();
        City city1 = new City(1, "Mar del Plata", "223", null);
        City city2 = new City(2, "Buenos Aires", "11", null);
        cities.add(city1);
        cities.add(city2);

        when(cityService.getAll(null)).thenReturn(cities);

        List<City> cityList = cityController.getAll(null);
        assertEquals(2, cityList.size());
        verify(cityService, times(1)).getAll(null);
    }

    @Test
    public void getAllEmptyTest()
    {
        List<City> cities = new ArrayList<City>();
        when(cityService.getAll(null)).thenReturn(cities);
        List<City> citiesResult = cityController.getAll(null);
        assertEquals(cities, citiesResult);
    }

    @Test
    public void getByIdOk() throws CityNotExistsException
    {
        List<City> cities = new ArrayList<City>();
        City city1 = new City(1, "Mar del Plata", "223", null);
        City city2 = new City(2, "Buenos Aires", "11", null);
        cities.add(city1);
        cities.add(city2);

        Optional<City> cityOptional = Optional.ofNullable(cities.get(0));

        when(cityService.getById(1)).thenReturn(cityOptional);

        Optional<City> cityResult = cityController.getById(1);

        assertEquals(cityOptional, cityResult);
        verify(cityService, times(1)).getById(1);
    }

    @Test(expected = CityNotExistsException.class)
    public void getByIdCityNotExists() throws CityNotExistsException
    {
        when(cityService.getById(1)).thenReturn(null);
        cityService.getById(1);
    }


    @Test
    public void getByPrefixOk() throws CityNotExistsException
    {
        List<City> cities = new ArrayList<City>();
        City city1 = new City(1, "Mar del Plata", "223", null);
        City city2 = new City(2, "Buenos Aires", "11", null);
        cities.add(city1);
        cities.add(city2);

        when(cityService.getByPrefix("223")).thenReturn(cities.get(0));

        City cityResult = cityController.getByPrefix("223");
        assertEquals(cities.get(0), cityResult);
        verify(cityService, times(1)).getByPrefix("223");
    }

    @Test(expected = CityNotExistsException.class)
    public void findByPrefixCityNotExistsTest() throws CityNotExistsException
    {
        when(cityService.getByPrefix("223")).thenReturn(null);
        cityController.getByPrefix("223");
    }

    //test de uploadCities??
}
