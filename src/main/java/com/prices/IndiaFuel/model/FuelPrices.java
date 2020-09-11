package com.prices.IndiaFuel.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FuelPrices implements Serializable {

    private String priceDate;
    private boolean error;
    private int androidLatestVersion;
    private List<Prices> prices;

}
