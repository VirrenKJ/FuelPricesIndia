package com.prices.IndiaFuel.model;

import java.io.Serializable;
import java.util.List;


public class FuelPrices implements Serializable {

    private String priceDate;
    private boolean error;
    private int androidLatestVersion;
    private List<Prices> prices;


    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(String priceDate) {
        this.priceDate = priceDate;
    }

    public int getAndroidLatestVersion() {
        return androidLatestVersion;
    }

    public void setAndroidLatestVersion(int androidLatestVersion) {
        this.androidLatestVersion = androidLatestVersion;
    }

    public List<Prices> getFuelPriceList() {
        return prices;
    }

    public void setFuelPriceList(List<Prices> prices) {
        this.prices = prices;
    }
}
