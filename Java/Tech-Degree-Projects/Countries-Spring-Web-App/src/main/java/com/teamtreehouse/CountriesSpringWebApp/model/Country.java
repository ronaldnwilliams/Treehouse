package com.teamtreehouse.CountriesSpringWebApp.model;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class Country {

    private String name;
    private String population;
    private String capitalCity;
    private List<String> officialLanguages;

    public Country(String name, long population, String capitalCity, String officialLanguage) {
        this.name = name;
        this.population = NumberFormat.getInstance().format(population);
        this.capitalCity = capitalCity;
        this.officialLanguages = new ArrayList<>(Arrays.asList(officialLanguage.split(",")));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getCapitalCity() {
        return capitalCity;
    }

    public void setCapitalCity(String capitalCity) {
        this.capitalCity = capitalCity;
    }

    public List<String> getOfficialLanguages() {
        return officialLanguages;
    }
}
