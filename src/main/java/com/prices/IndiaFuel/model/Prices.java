package com.prices.IndiaFuel.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter @Setter @NoArgsConstructor
public class Prices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int priceId;
    private int cityId;
    private String cityName;
    private String stateName;
    private int pPrice;
    private int dPrice;
    private int pDiff;
    private int dDiff;
    private String priceDate;

}