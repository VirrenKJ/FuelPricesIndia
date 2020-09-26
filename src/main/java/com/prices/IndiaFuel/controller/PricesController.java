package com.prices.IndiaFuel.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.prices.IndiaFuel.model.FuelPrices;
import com.prices.IndiaFuel.model.Prices;
import com.prices.IndiaFuel.service.PricesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PricesController {

    @Autowired
    private PricesService pricesService;

    @PostMapping(value = "saveFuelPricesManually")
    public void jsonDataCPost() throws UnirestException {
        pricesService.jsonDataSPost();
    }

    @GetMapping("fuelPrice")
    public FuelPrices fuelPricesByDateC(@RequestParam(value = "date") String priceDate) throws UnirestException {
        System.out.println("A::");
        return pricesService.fuelPricesByDateS(priceDate);
    }

    @GetMapping("fuelPrice/city/{cityName}")
    public List<Prices> fuelPricesByCityC(@PathVariable String cityName) throws UnirestException {
        return pricesService.fuelPricesByCityS(cityName);
    }

    @GetMapping("fuelPrice/state/{stateName}")
    public List<Prices> fuelPricesByStateC(@PathVariable String stateName) throws UnirestException {
        return pricesService.fuelPricesByStateS(stateName);
    }

    @GetMapping("fuelPrice/lastTenDays")
    public List<Prices> fuelPriceByCityLastTenDaysC(@RequestParam(value = "cityName") String cityName,
                                                    @RequestParam(value = "priceDate") String priceDate) {
        return pricesService.fuelPriceByCityLastTenDaysS(cityName, priceDate);
    }
}
