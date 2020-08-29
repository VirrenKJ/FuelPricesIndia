package com.prices.IndiaFuel.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Prices implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public int getpPrice() {
        return pPrice;
    }

    public void setpPrice(int pPrice) {
        this.pPrice = pPrice;
    }

    public int getdPrice() {
        return dPrice;
    }

    public void setdPrice(int dPrice) {
        this.dPrice = dPrice;
    }

    public int getpDiff() {
        return pDiff;
    }

    public void setpDiff(int pDiff) {
        this.pDiff = pDiff;
    }

    public int getdDiff() {
        return dDiff;
    }

    public void setdDiff(int dDiff) {
        this.dDiff = dDiff;
    }

    public String getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(String priceDate) {
        this.priceDate = priceDate;
    }

}
