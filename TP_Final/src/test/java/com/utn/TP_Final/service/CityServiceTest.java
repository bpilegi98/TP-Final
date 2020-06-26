package com.utn.TP_Final.service;

import com.utn.TP_Final.exceptions.CityAlreadyExistsException;
import com.utn.TP_Final.exceptions.CityNotExistsException;
import com.utn.TP_Final.model.City;
import com.utn.TP_Final.model.Province;
import com.utn.TP_Final.repository.CityRepository;
import com.utn.TP_Final.repository.ProvinceRepository;
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

public class CityServiceTest {

    @Autowired
    CityService cityService;
    @Mock
    ProvinceRepository provinceRepository;

    @Mock
    CityRepository cityRepository;

    @Before
    public void setUp()
    {
        initMocks(this);
        cityService = new CityService(cityRepository,provinceRepository);
    }

    @Test
    public void addCityTest() throws CityAlreadyExistsException
    {
        City city = new City(1, "Mar del Plata", "223", null);
        when(cityRepository.save(city)).thenReturn(city);
        City cityResult = cityService.addCity(city);
        assertEquals(city.getName(), cityResult.getName());
        assertEquals(city.getPrefixNumber(), cityResult.getPrefixNumber());
    }

    @Test(expected = CityAlreadyExistsException.class)
    public void addCityAlreadyExists() throws CityAlreadyExistsException
    {
        City city = new City(1, "Mar del Plata", "223", null);
        when(cityRepository.save(city)).thenReturn(null);
        cityService.addCity(city);
    }

    @Test
    public void deleteCityOk() throws CityNotExistsException
    {
        City city = new City(1, "Mar del Plata", "223", null);
        when(cityRepository.delete(1)).thenReturn(city);
        City cityResult = cityService.deleteCity(1);
        assertEquals(city, cityResult);
    }

    @Test(expected = CityNotExistsException.class)
    public void deleteCityNotExists() throws CityNotExistsException
    {
        when(cityRepository.delete(1)).thenReturn(null);
        cityService.deleteCity(1);
    }

    @Test
    public void getAllTest()
    {
        List<City> cities = new ArrayList<City>();
        City city1 = new City(1, "Mar del Plata", "223", null);
        City city2 = new City(2, "Buenos Aires", "11", null);
        cities.add(city1);
        cities.add(city2);

        when(cityRepository.findAll()).thenReturn(cities);

        List<City> cityList = cityService.getAll(null);
        assertEquals(2, cityList.size());
        verify(cityRepository, times(1)).findAll();
    }

    @Test
    public void getAllEmptyTest()
    {
        List<City> cities = new ArrayList<City>();
        when(cityRepository.findAll()).thenReturn(cities);
        List<City> citiesResult = cityService.getAll(null);
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

        when(cityRepository.findById(1)).thenReturn(cityOptional);

        Optional<City> cityResult = cityService.getById(1);

        assertEquals(cityOptional, cityResult);
        verify(cityRepository, times(1)).findById(1);
    }

    @Test(expected = CityNotExistsException.class)
    public void getByIdCityNotExists() throws CityNotExistsException
    {
        when(cityRepository.findById(1)).thenReturn(null);
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

        when(cityRepository.findByPrefix("223")).thenReturn(cities.get(0));

        City cityResult = cityService.getByPrefix("223");
        assertEquals(cities.get(0), cityResult);
        verify(cityRepository, times(1)).findByPrefix("223");
    }

    @Test(expected = CityNotExistsException.class)
    public void findByPrefixCityNotExistsTest() throws CityNotExistsException
    {
        when(cityRepository.findByPrefix("223")).thenReturn(null);
        cityService.getByPrefix("223");
    }

    //test de uploadCities??


}
