package com.utn.TP_Final.controller;


import com.utn.TP_Final.model.Province;
import com.utn.TP_Final.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/")
@RequestMapping("/province")
public class ProvinceController {

    private final ProvinceService provinceService;

    @Autowired
    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @PostMapping("/")
    public void addProvince(Province newProvince)
    {
        provinceService.addProvince(newProvince);
    }

    @GetMapping("/")
    public List<Province> getAll(@RequestParam(required = false)String name)
    {
        return provinceService.getAll(name);
    }
}
