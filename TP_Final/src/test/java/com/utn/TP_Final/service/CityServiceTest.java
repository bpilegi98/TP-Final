package com.utn.TP_Final.service;

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


}
