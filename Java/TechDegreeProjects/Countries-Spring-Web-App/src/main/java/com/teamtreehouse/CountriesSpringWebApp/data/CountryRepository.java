package com.teamtreehouse.CountriesSpringWebApp.data;

import com.teamtreehouse.CountriesSpringWebApp.model.Country;
import javassist.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Component
public class CountryRepository {

    private static final List<Country> ALL_COUNTRIES = Arrays.asList(
            new Country("Ecuador", 16625776, "Quito", "Spanish, Kichwa (Quichua), Shuar, others \"are in official use for indigenous peoples\""),
            new Country("India", 1342512706, "New Delhi", "Hindi, English, Eight Schedule"),
            new Country("France", 64938716, "Paris", "French"),
            new Country("Kenya", 48466928, "Nairobi", "Kiswahili"),
            new Country("Italy", 59797978, "Rome", "Italian")
    );

    public List<Country> getAllCountries() {
        return ALL_COUNTRIES;
    }

    public List<Country> getAllCountriesByName() {
        ALL_COUNTRIES.sort(Comparator.comparing(Country::getName));
        return ALL_COUNTRIES;
    }

    public List<Country> getAllCountriesByPopulation() {
        ALL_COUNTRIES.sort(Comparator.comparing(Country::getPopulation));
        return ALL_COUNTRIES;
    }

    public Country findByName(String name) {
        return ALL_COUNTRIES.stream()
                .filter(country -> country.getName().equals(name))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    public class NotFoundException extends RuntimeException {
    }
}
