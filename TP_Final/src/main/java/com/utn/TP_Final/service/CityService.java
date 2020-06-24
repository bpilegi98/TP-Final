package com.utn.TP_Final.service;

import com.utn.TP_Final.model.City;
import com.utn.TP_Final.model.Province;
import com.utn.TP_Final.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import com.utn.TP_Final.repository.ProvinceRepository;

import static java.util.Objects.isNull;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final ProvinceRepository provinceRepository;

    @Autowired
    public CityService(CityRepository cityRepository, ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
        this.cityRepository = cityRepository;
    }

    public void addCity(City newCity)
    {
        cityRepository.save(newCity);
    }

    public void deleteCity(City city)
    {
        cityRepository.delete(city);
    }

    public List<City> getAll(String name)
    {
        if(isNull(name))
        {
            return cityRepository.findAll();
        }
        return cityRepository.findByName(name);
    }

    public Optional<City> getById(Integer id)
    {
        return cityRepository.findById(id);
    }

    public City getByPrefix(String prefix)
    {
        return cityRepository.findByPrefix(prefix);
    }

    public void uploadCites() throws IOException {
        List<String> lista = Files.readAllLines(Paths.get("C:\\Users\\Juan\\Desktop\\cityPrefix.txt"), StandardCharsets.ISO_8859_1);
        List<String> ciudades = new ArrayList<>();
        String prefix = null;
        Province province = new Province();
        City newCity= new City();
        String aux = " ";

        int i=0;
        for(String s: lista){
            if(s.isEmpty()){
                i++;
                if(i>2) i=0;
            }
            else{
                if(i==0){//Es un prefijo
                    prefix=s;
                    System.out.println("prefix: "+s+"\n");
                }
                if(i==1){//es una provincia
                    System.out.println("provincia= "+s+"\n");
                    if(s.equals("Rio")){
                        s="Rio Negro";
                    }
                    province= provinceRepository.findByName(s).get(0);
                }
                if(i==2){//es una lista de ciudades
                    ciudades = Arrays.asList(s.split(","));
                    for(String c: ciudades){
                        if(Character.isWhitespace(c.charAt(0))){
                            c=c.substring(1);
                        }
                        newCity = new City(c,prefix,province);// aca puede ir el metodo q lo guarde en la bd
                        cityRepository.save(newCity);
                    }
                }
            }
        }

    }
}
