package com.utn.TP_Final.service;

import com.utn.TP_Final.model.Province;
import com.utn.TP_Final.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class ProvinceService {

    private final ProvinceRepository provinceRepository;

    @Autowired
    public ProvinceService(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    public void addProvince(Province newProvince)
    {
        provinceRepository.save(newProvince);
    }

    public List<Province> getAll(String name)
    {
        if(isNull(name))
        {
            return provinceRepository.findAll();
        }
        return provinceRepository.findByName(name);
    }

    public Optional<Province> getById(Integer id)
    {
        return provinceRepository.findById(id);
    }

}
