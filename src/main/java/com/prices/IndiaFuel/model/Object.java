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
public class Object {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private boolean error;
    private String priceDate;

    @OneToMany( mappedBy = "object", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prices> prices;


    //why we create parameterized constructor??????????????????
}
