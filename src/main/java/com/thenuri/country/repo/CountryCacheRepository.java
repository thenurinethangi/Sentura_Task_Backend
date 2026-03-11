package com.thenuri.country.repo;

import com.thenuri.country.model.Country;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CountryCacheRepository {
    private final ConcurrentHashMap<String, List<Country>> cache = new ConcurrentHashMap<>();
    private long lastRefresh = 0;

    public List<Country> getCountries() {
        return cache.get("all");
    }

    public void setCountries(List<Country> countries) {
        cache.put("all", countries);
        lastRefresh = System.currentTimeMillis();
    }

    public boolean isCacheValid() {
        return (System.currentTimeMillis() - lastRefresh) < 10 * 60 * 1000;
    }

    public void clearCache() {
        cache.clear();
        lastRefresh = 0;
    }
}
