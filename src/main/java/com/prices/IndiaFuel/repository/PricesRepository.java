package com.prices.IndiaFuel.repository;

import com.prices.IndiaFuel.model.Prices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PricesRepository extends JpaRepository<Prices, Long> {
    List<Prices> findByCityName(String cityName);

    List<Prices> findByPriceDate(String priceDate);

    List<Prices> findByStateName(String stateName);

    @Query(value = "select * from prices where city_name = ?1 and price_date<= ?2 limit 10;", nativeQuery = true)
    List<Prices> findByPriceDateLimit(String cityName, String priceDate);
}
