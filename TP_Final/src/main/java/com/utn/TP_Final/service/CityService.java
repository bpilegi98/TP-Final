package com.utn.TP_Final.service;

import com.utn.TP_Final.model.City;
import com.utn.TP_Final.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public void addCity(City newCity)
    {
        cityRepository.save(newCity);
    }

    public List<City> getAll(String name)
    {
        if(isNull(name))
        {
            return cityRepository.findAll();
        }
        return cityRepository.findByName(name);
    }
}
