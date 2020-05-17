package com.utn.TP_Final.service;


import com.utn.TP_Final.model.Country;
import com.utn.TP_Final.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class CountryService {

    private final CountryRepository countryRepository;


    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }


    public void addCountry(Country newCountry)
    {
        countryRepository.save(newCountry);
    }

    public void deleteCountry(Country country)
    {
        countryRepository.delete(country);
    }

    public List<Country> getAll(String name)
    {
        if(isNull(name))
        {
            return countryRepository.findAll();
        }
        return countryRepository.findByName(name);
    }

    public Optional<Country> findById(Integer id)
    {
        return countryRepository.findById(id);
    }

}
