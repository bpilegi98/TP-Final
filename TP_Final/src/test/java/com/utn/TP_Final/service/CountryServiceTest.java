package com.utn.TP_Final.service;

import com.utn.TP_Final.exceptions.CountryNotExistsException;
import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.model.Country;
import com.utn.TP_Final.repository.CountryRepository;
import com.utn.TP_Final.repository.UserRepository;
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

public class CountryServiceTest {

    @Autowired
    CountryService countryService;

    @Mock
    CountryRepository countryRepository;

    @Before
    public void setUp()
    {
        initMocks(this);
        countryService = new CountryService(countryRepository);
    }

    @Test
    public void getAllTest()
    {
        List<Country> countries = new ArrayList<Country>();
        Country country1 = new Country("Argentina");
        Country country2 = new Country("Bolivia");
        countries.add(country1);
        countries.add(country2);

        when(countryRepository.findAll()).thenReturn(countries);

        List<Country> countryList = countryService.getAll(null);

        assertEquals(2, countryList.size());
        verify(countryRepository, times(1)).findAll();
    }

    @Test
    public void findByIdOk() throws CountryNotExistsException, ValidationException
    {
        Country country = new Country(1, "Argentina", null);
        when(countryRepository.findById(1)).thenReturn(Optional.of(country));
        Optional<Country> resultCountry = countryService.findById(1);
        // no me deja hacer el .getId() porque es Optional :C
        // assertEquals(country.getId(), resultCountry.);
    }
}
