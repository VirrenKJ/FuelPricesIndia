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
import java.util.List;
import java.util.Optional;

@Service
public class PricesService {

    @Autowired
    private PricesRepository pricesRepository;

    public void jsonDataSPost() throws UnirestException{
        HttpResponse<JsonNode> httpResponse = Unirest.get("http://yptech.in/petrolprice/priceapi.php?priceDate=22-07-2020")
                .asJson();
        JSONObject object = httpResponse.getBody().getObject();
        FuelPrices fuelPrices = new FuelPrices();
        fuelPrices.setError(object.optBoolean("error"));
        fuelPrices.setPriceDate(object.optString("priceDate"));
        fuelPrices.setAndroidLatestVersion(object.optInt("androidLatestVersion"));

        JSONArray jsonArray = object.getJSONArray("prices");
        ArrayList<Prices> byPriceDate = new ArrayList<>();
        for(int i =0 ; i < jsonArray.length() ; i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Prices price = new Prices();
            price.setPriceId(jsonObject.optLong("priceId"));
            price.setCityId(jsonObject.getInt("cityId"));
            price.setCityName(jsonObject.getString("cityName"));
            price.setStateName(jsonObject.getString("stateName"));
            price.setPPrice(jsonObject.getInt("pPrice"));
            price.setDPrice(jsonObject.getInt("dPrice"));
            price.setPDiff(jsonObject.getInt("pDiff"));
            price.setDDiff(jsonObject.getInt("dDiff"));
            price.setPriceDate(jsonObject.getString("priceDate"));
            byPriceDate.add(price);
            pricesRepository.save(price);
        }
        fuelPrices.setPrices(byPriceDate);
    }

    public FuelPrices fuelPricesByDateS(String priceDate) throws UnirestException {
        FuelPrices fuelPrices = new FuelPrices();

        List<Prices> byPriceDate = pricesRepository.findByPriceDate(priceDate);
        if (byPriceDate.size() == 0) {
            System.out.println("priceDate::" + priceDate);
            String baseURL = "http://yptech.in/petrolprice/priceapi.php?priceDate=";
            String finalURL = baseURL + priceDate;
            System.out.println("FINAL URL ::" + finalURL);
            HttpResponse<JsonNode> httpResponse = null;
            httpResponse = Unirest.get(finalURL)
                    .asJson();
            JSONObject object = httpResponse.getBody().getObject();
            fuelPrices.setPriceDate(object.optString("priceDate"));
            JSONArray jsonArray = object.getJSONArray("prices");
            ArrayList<Prices> priceList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Prices price = new Prices();
                price.setPriceId(jsonObject.optLong("priceId"));
                price.setCityId(jsonObject.getInt("cityId"));
                price.setCityName(jsonObject.getString("cityName"));
                price.setStateName(jsonObject.getString("stateName"));
                price.setPPrice(jsonObject.getInt("pPrice"));
                price.setDPrice(jsonObject.getInt("dPrice"));
                price.setPDiff(jsonObject.getInt("pDiff"));
                price.setDDiff(jsonObject.getInt("dDiff"));
                price.setPriceDate(jsonObject.getString("priceDate"));
                priceList.add(price);
                pricesRepository.save(price);
            }
            fuelPrices.setPrices(priceList);
            fuelPrices.setError(object.optBoolean("error"));
            fuelPrices.setAndroidLatestVersion(object.optInt("androidLatestVersion"));
            return fuelPrices;
        }else {
            fuelPrices.setPrices(pricesRepository.findByPriceDate(priceDate));
            fuelPrices.setPriceDate(priceDate);
            return fuelPrices;
        }
    }

    public List<Prices> fuelPricesByCityS(String cityName) throws UnirestException {
        if (pricesRepository.findByCityName(cityName).size() == 0) {
            jsonDataSPost();
        }
        return pricesRepository.findByCityName(cityName);
    }

    public List<Prices> fuelPricesByStateS(String stateName) throws UnirestException {
        if (pricesRepository.findByStateName(stateName).size() == 0) {
            jsonDataSPost();
        }
        return pricesRepository.findByStateName(stateName);
    }

    public List<Prices> fuelPriceByCityLastTenDaysS(String cityName, String priceDate){
        return pricesRepository.findByPriceDateLimit(cityName, priceDate);
    }
//    public List<Prices> fuelPriceByCityLastTenDaysS(String cityName, String priceDate) {
//        String[] arrOfPriceDate = priceDate.split("-", 3);
//        String priceYear = arrOfPriceDate[0];
//        String priceMonth = arrOfPriceDate[1];
//        String priceDay = arrOfPriceDate[2];
//        int intPriceYear = Integer.parseInt(priceYear);
//        int intPriceMonth = Integer.parseInt(priceMonth);
//        int intPriceDay = Integer.parseInt(priceDay);
//
//        List<Prices> prices = new ArrayList<>();
//        //for changing years, in month of january, if date is less that Jan 10th
//        if (intPriceMonth == 1 && intPriceDay < 10){
//            for(int i =intPriceDay; i > 0; i--){
//                int newIntPriceDay = i;
//                int newIntPriceMonth = intPriceMonth;
//                int newIntPriceYear = intPriceYear;
//                String newStringPriceDay = Integer.toString(newIntPriceDay);
//                String newStringPriceMonth = Integer.toString(newIntPriceMonth);
//                String newStringPriceYear = Integer.toString(newIntPriceYear);
//                String newPriceDate = String.join(newStringPriceDay, newStringPriceMonth, newStringPriceYear);
//                Prices pricesByCityAndDate = pricesRepository.findByCityNameAndPriceDate(cityName, newPriceDate);
//                prices.add(pricesByCityAndDate);
//            }
//            for(int i =32-(10-intPriceDay); i < 32; i++){
//                int newIntPriceDay = i;
//                int newIntPriceMonth = 12;
//                int newIntPriceYear = intPriceYear-1;
//                String newStringPriceDay = Integer.toString(newIntPriceDay);
//                String newStringPriceMonth = Integer.toString(newIntPriceMonth);
//                String newStringPriceYear = Integer.toString(newIntPriceYear);
//                String newPriceDate = String.join(newStringPriceDay, newStringPriceMonth, newStringPriceYear);
//                Prices pricesByCityAndDate = pricesRepository.findByCityNameAndPriceDate(cityName, newPriceDate);
//                prices.add(pricesByCityAndDate);
//            }
//        }
//
//        //to check leap year
//        boolean leapYear = true ;
//        if (intPriceYear % 4 == 0) {
//            if (intPriceYear % 100 == 0) {
//                if (intPriceYear % 400 ==0) {
//                    leapYear = true;
//                }
//                else{
//                    leapYear= false;
//                }
//            }
//            else{
//                leapYear=true;
//            }
//        }
//        else{
//            leapYear = false;
//        }
//
//        //for entering into month of february
//        if (leapYear == false){
//            if (intPriceMonth == 3 && intPriceDay < 10){
//                for(int i =intPriceDay; i > 0; i--){
//                    int newIntPriceDay = i;
//                    int newIntPriceMonth = intPriceMonth;
//                    int newIntPriceYear = intPriceYear;
//                    String newStringPriceDay = Integer.toString(newIntPriceDay);
//                    String newStringPriceMonth = Integer.toString(newIntPriceMonth);
//                    String newStringPriceYear = Integer.toString(newIntPriceYear);
//                    String newPriceDate = String.join(newStringPriceDay, newStringPriceMonth, newStringPriceYear);
//                    Prices pricesByCityAndDate = pricesRepository.findByCityNameAndPriceDate(cityName, newPriceDate);
//                    prices.add(pricesByCityAndDate);
//                }
//                for(int i =29-(10-intPriceDay); i < 29; i++){
//                    int newIntPriceDay = i;
//                    int newIntPriceMonth = intPriceMonth-1;
//                    int newIntPriceYear = intPriceYear;
//                    String newStringPriceDay = Integer.toString(newIntPriceDay);
//                    String newStringPriceMonth = Integer.toString(newIntPriceMonth);
//                    String newStringPriceYear = Integer.toString(newIntPriceYear);
//                    String newPriceDate = String.join(newStringPriceDay, newStringPriceMonth, newStringPriceYear);
//                    Prices pricesByCityAndDate = pricesRepository.findByCityNameAndPriceDate(cityName, newPriceDate);
//                    prices.add(pricesByCityAndDate);
//                }
//            }
//        }
//        else{
//            if (intPriceMonth == 3 && intPriceDay < 10){
//                for(int i =intPriceDay; i > 0; i--){
//                    int newIntPriceDay = i;
//                    int newIntPriceMonth = intPriceMonth;
//                    int newIntPriceYear = intPriceYear;
//                    String newStringPriceDay = Integer.toString(newIntPriceDay);
//                    String newStringPriceMonth = Integer.toString(newIntPriceMonth);
//                    String newStringPriceYear = Integer.toString(newIntPriceYear);
//                    String newPriceDate = String.join(newStringPriceDay, newStringPriceMonth, newStringPriceYear);
//                    Prices pricesByCityAndDate = pricesRepository.findByCityNameAndPriceDate(cityName, newPriceDate);
//                    prices.add(pricesByCityAndDate);
//                }
//                for(int i =30-(10-intPriceDay); i < 30; i++){
//                    int newIntPriceDay = i;
//                    int newIntPriceMonth = intPriceMonth-1;
//                    int newIntPriceYear = intPriceYear;
//                    String newStringPriceDay = Integer.toString(newIntPriceDay);
//                    String newStringPriceMonth = Integer.toString(newIntPriceMonth);
//                    String newStringPriceYear = Integer.toString(newIntPriceYear);
//                    String newPriceDate = String.join(newStringPriceDay, newStringPriceMonth, newStringPriceYear);
//                    Prices pricesByCityAndDate = pricesRepository.findByCityNameAndPriceDate(cityName, newPriceDate);
//                    prices.add(pricesByCityAndDate);
//                }
//            }
//        }
//
//        //for entering into months with 31 days
//        if (intPriceMonth == 2 || intPriceMonth == 4 || intPriceMonth == 6 || intPriceMonth == 8
//                || intPriceMonth == 9 || intPriceMonth == 11 && intPriceDay < 10){
//            for(int i =intPriceDay; i > 0; i--){
//                int newIntPriceDay = i;
//                int newIntPriceMonth = intPriceMonth;
//                int newIntPriceYear = intPriceYear;
//                String newStringPriceDay = Integer.toString(newIntPriceDay);
//                String newStringPriceMonth = Integer.toString(newIntPriceMonth);
//                String newStringPriceYear = Integer.toString(newIntPriceYear);
//                String newPriceDate = String.join(newStringPriceDay, newStringPriceMonth, newStringPriceYear);
//                Prices pricesByCityAndDate = pricesRepository.findByCityNameAndPriceDate(cityName, newPriceDate);
//                prices.add(pricesByCityAndDate);
//            }
//            for(int i =32-(10-intPriceDay); i < 32; i++){
//                int newIntPriceDay = i;
//                int newIntPriceMonth = intPriceMonth-1;
//                int newIntPriceYear = intPriceYear;
//                String newStringPriceDay = Integer.toString(newIntPriceDay);
//                String newStringPriceMonth = Integer.toString(newIntPriceMonth);
//                String newStringPriceYear = Integer.toString(newIntPriceYear);
//                String newPriceDate = String.join(newStringPriceDay, newStringPriceMonth, newStringPriceYear);
//                Prices pricesByCityAndDate = pricesRepository.findByCityNameAndPriceDate(cityName, newPriceDate);
//                prices.add(pricesByCityAndDate);
//            }
//        }
//        if (intPriceMonth == 2 || intPriceMonth == 4 || intPriceMonth == 6 || intPriceMonth == 8 || intPriceMonth == 9
//                || intPriceMonth == 11 && intPriceDay >= 10){
//            for(int i =intPriceDay; i > intPriceDay-10; i--){
//                int newIntPriceDay = i;
//                int newIntPriceMonth = intPriceMonth;
//                int newIntPriceYear = intPriceYear;
//                String newStringPriceDay = Integer.toString(newIntPriceDay);
//                String newStringPriceMonth = Integer.toString(newIntPriceMonth);
//                String newStringPriceYear = Integer.toString(newIntPriceYear);
//                String newPriceDate = String.join(newStringPriceDay, newStringPriceMonth, newStringPriceYear);
//                Prices pricesByCityAndDate = pricesRepository.findByCityNameAndPriceDate(cityName, newPriceDate);
//                prices.add(pricesByCityAndDate);
//            }
//        }
//
//        //for entering into months with 30 days
//        if (intPriceMonth == 5 || intPriceMonth == 7 || intPriceMonth == 10 || intPriceMonth == 12 && intPriceDay < 10){
//            for(int i =intPriceDay; i > 0; i--){
//                int newIntPriceDay = i;
//                int newIntPriceMonth = intPriceMonth;
//                int newIntPriceYear = intPriceYear;
//                String newStringPriceDay = Integer.toString(newIntPriceDay);
//                String newStringPriceMonth = Integer.toString(newIntPriceMonth);
//                String newStringPriceYear = Integer.toString(newIntPriceYear);
//                String newPriceDate = String.join(newStringPriceDay, newStringPriceMonth, newStringPriceYear);
//                Prices pricesByCityAndDate = pricesRepository.findByCityNameAndPriceDate(cityName, newPriceDate);
//                prices.add(pricesByCityAndDate);
//            }
//            for(int i =31-(10-intPriceDay); i < 31; i++){
//                int newIntPriceDay = i;
//                int newIntPriceMonth = intPriceMonth-1;
//                int newIntPriceYear = intPriceYear;
//                String newStringPriceDay = Integer.toString(newIntPriceDay);
//                String newStringPriceMonth = Integer.toString(newIntPriceMonth);
//                String newStringPriceYear = Integer.toString(newIntPriceYear);
//                String newPriceDate = String.join(newStringPriceDay, newStringPriceMonth, newStringPriceYear);
//                Prices pricesByCityAndDate = pricesRepository.findByCityNameAndPriceDate(cityName, newPriceDate);
//                prices.add(pricesByCityAndDate);
//            }
//        }
//        if (intPriceMonth == 1 || intPriceMonth == 3 || intPriceMonth == 5 || intPriceMonth == 7 || intPriceMonth == 10
//                || intPriceMonth == 12 && intPriceDay >= 10){
//            for(int i =intPriceDay; i > intPriceDay-10; i--){
//                int newIntPriceDay = i;
//                int newIntPriceMonth = intPriceMonth;
//                int newIntPriceYear = intPriceYear;
//                String newStringPriceDay = Integer.toString(newIntPriceDay);
//                String newStringPriceMonth = Integer.toString(newIntPriceMonth);
//                String newStringPriceYear = Integer.toString(newIntPriceYear);
//                String newPriceDate = String.join(newStringPriceDay, newStringPriceMonth, newStringPriceYear);
//                Prices pricesByCityAndDate = pricesRepository.findByCityNameAndPriceDate(cityName, newPriceDate);
//                prices.add(pricesByCityAndDate);
//            }
//        }
//        return prices;
//    }
}
