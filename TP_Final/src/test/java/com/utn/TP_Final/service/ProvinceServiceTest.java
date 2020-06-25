package com.utn.TP_Final.service;

import com.utn.TP_Final.exceptions.ProvinceNotExistsException;
import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.model.Province;
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

public class ProvinceServiceTest {

    @Autowired
    ProvinceService provinceService;

    @Mock
    ProvinceRepository provinceRepository;

    @Before
    public void setUp()
    {
        initMocks(this);
        provinceService = new ProvinceService(provinceRepository);
    }

    @Test
    public void getAllTest()
    {
        List<Province> provinces = new ArrayList<Province>();
        Province p1 = new Province(1, "Buenos Aires", null, null);
        Province p2 = new Province(2, "Jujuy", null, null);
        provinces.add(p1);
        provinces.add(p2);

        when(provinceRepository.findAll()).thenReturn(provinces);

        List<Province> provinceList = provinceService.getAll(null);
        assertEquals(2, provinceList.size());
        verify(provinceRepository, times(1)).findAll();
    }

    @Test
    public void getByIdOk() throws ProvinceNotExistsException, ValidationException {
        List<Province> provinces = new ArrayList<Province>();
        Province p1 = new Province(1, "Buenos Aires", null, null);
        Province p2 = new Province(2, "Jujuy", null, null);
        provinces.add(p1);
        provinces.add(p2);

        Optional<Province> provinceOptional = Optional.ofNullable(provinces.get(0));

        when(provinceRepository.findById(1)).thenReturn(provinceOptional);

        Optional<Province> provinceResult = provinceService.getById(1);

        assertEquals(provinceOptional, provinceResult);
        verify(provinceRepository, times(1)).findById(1);
    }

    @Test(expected = ProvinceNotExistsException.class)
    public void getByIdProvinceNotExists() throws ProvinceNotExistsException, ValidationException {
        when(provinceRepository.findById(1)).thenReturn(null);
        provinceService.getById(1);
    }

}
