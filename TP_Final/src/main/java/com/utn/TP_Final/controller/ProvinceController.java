package com.utn.TP_Final.controller;


import com.utn.TP_Final.model.Province;
import com.utn.TP_Final.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("")
@RequestMapping("/province")
public class ProvinceController {

    private final ProvinceService provinceService;

    @Autowired
    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @PostMapping("/")
    public void addProvince(@RequestBody Province newProvince)
    {
        provinceService.addProvince(newProvince);
    }

    @GetMapping("/")
    public List<Province> getAll(@RequestParam(required = false)String name)
    {
        return provinceService.getAll(name);
    }

    @GetMapping("/{id}")
    public Optional<Province> getById(@RequestParam(required = true)Integer id)
    {
        return provinceService.getById(id);
    }
}
