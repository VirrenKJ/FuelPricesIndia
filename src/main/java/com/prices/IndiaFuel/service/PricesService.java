package com.prices.IndiaFuel.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.prices.IndiaFuel.model.FuelPrices;
import com.prices.IndiaFuel.model.Prices;
import com.prices.IndiaFuel.repository.PricesRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PricesService {

    @Autowired
    private PricesRepository pricesRepository;

    public FuelPrices jsonDataSPost() throws UnirestException {
        HttpResponse<JsonNode> httpResponse = Unirest.get("http://yptech.in/petrolprice/priceapi.php?priceDate=22-07-2020")
                .asJson();
        JSONObject object = httpResponse.getBody().getObject();

        FuelPrices fuelPrices = new FuelPrices();
        fuelPrices.setError(object.optBoolean("error"));
        fuelPrices.setPriceDate(object.optString("priceDate"));

        JSONArray jsonArray = object.getJSONArray("prices");
        ArrayList<Prices> prices = new ArrayList<>();
        for(int i =0 ; i < jsonArray.length() ; i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Prices price = new Prices();
            price.setPriceId(jsonObject.optLong("priceId"));
            price.setCityId(jsonObject.getInt("cityId"));
            price.setCityName(jsonObject.getString("cityName"));
            price.setStateName(jsonObject.getString("stateName"));
            price.setpPrice(jsonObject.getInt("pPrice"));
            price.setdPrice(jsonObject.getInt("dPrice"));
            price.setpDiff(jsonObject.getInt("pDiff"));
            price.setdDiff(jsonObject.getInt("dDiff"));
            price.setPriceDate(jsonObject.getString("priceDate"));
            prices.add(price);
            pricesRepository.save(price);
        }
        fuelPrices.setFuelPriceList(prices);

        fuelPrices.setAndroidLatestVersion(object.optInt("androidLatestVersion"));
        return fuelPrices;
    }

}
