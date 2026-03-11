package com.thenuri.country.service;

import com.thenuri.country.model.Country;
import com.thenuri.country.repo.CountryCacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryService {
    private static final String BASE_URL = "https://restcountries.com/v3.1";
    private static final String FIELDS = "name,capital,region,population,flags";

    @Autowired
    private CountryCacheRepository cacheRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Country> getAllCountries() {
        if (cacheRepository.isCacheValid() && cacheRepository.getCountries() != null) {
            return cacheRepository.getCountries();
        }
        String url = BASE_URL + "/all?fields=" + FIELDS;
        Country[] countries = restTemplate.getForObject(url, Country[].class);
        List<Country> countryList = Arrays.asList(countries);
        cacheRepository.setCountries(countryList);
        return countryList;
    }

    public List<Country> searchCountries(String name) {
        String url = UriComponentsBuilder.newInstance()
            .scheme("https")
            .host("restcountries.com")
            .path("/v3.1/name/" + name)
            .queryParam("fields", FIELDS)
            .build()
            .toUriString();
        Country[] countries = restTemplate.getForObject(url, Country[].class);
        return Arrays.asList(countries);
    }
}
