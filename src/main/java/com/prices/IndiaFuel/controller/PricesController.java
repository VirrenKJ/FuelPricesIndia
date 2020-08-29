package com.prices.IndiaFuel.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.prices.IndiaFuel.model.FuelPrices;
import com.prices.IndiaFuel.service.PricesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PricesController {

    @Autowired
    private PricesService pricesService;


    @GetMapping(value = "daily")
    public FuelPrices jsonDataSPost() throws UnirestException {
        return pricesService.jsonDataSPost();
    }

}
