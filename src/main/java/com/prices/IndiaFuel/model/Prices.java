package com.prices.IndiaFuel.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class Prices implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long priceId;
    private int cityId;
    private String cityName;
    private String stateName;
    private int pPrice;
    private int dPrice;
    private int pDiff;
    private int dDiff;
    private String priceDate;

}
