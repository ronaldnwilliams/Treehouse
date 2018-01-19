package com.teamtreehouse.CountriesSpringWebApp.controller;

import com.teamtreehouse.CountriesSpringWebApp.data.CountryRepository;
import com.teamtreehouse.CountriesSpringWebApp.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class CountryController {

    @Autowired
    private CountryRepository countryRepository;

    @RequestMapping("/")
    public String listCountries(ModelMap modelMap) {
        List<Country> allCountries = countryRepository.getAllCountries();
        modelMap.put("countries", allCountries);
        return "index";
    }

    @RequestMapping("/country/{name}")
    public String countryDetails(@PathVariable String name, ModelMap modelMap) {
        Country country = countryRepository.findByName(name);
        modelMap.put("country", country);
        return"country-details";
    }

    @RequestMapping("/name")
    public String listCountriesByName(ModelMap modelMap) {
        List<Country> allCountries = countryRepository.getAllCountriesByName();
        modelMap.put("countries", allCountries);
        return "index";
    }


    @RequestMapping("/population")
    public String listCountriesByPopulation(ModelMap modelMap) {
        List<Country> allCountries = countryRepository.getAllCountriesByPopulation();
        modelMap.put("countries", allCountries);
        return "index";
    }
}
