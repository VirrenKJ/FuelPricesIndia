package com.prices.IndiaFuel.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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

    @OneToMany( mappedBy = "prices", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Object> object;
}
