package com.prices.IndiaFuel.repository;

import com.prices.IndiaFuel.model.Prices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PricesRepository extends JpaRepository<Prices, Long> {
    //boolean findByPriceDate(String priceDate);

    List<Prices> findByCityName(String cityName);

    List<Prices> findByPriceDate(String priceDate);

    List<Prices> findByStateName(String stateName);

}
