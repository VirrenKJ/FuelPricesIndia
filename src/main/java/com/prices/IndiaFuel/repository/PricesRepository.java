package com.prices.IndiaFuel.repository;

import com.prices.IndiaFuel.model.Prices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PricesRepository extends JpaRepository<Prices, Long> {
    List<Prices> findByCityName(String cityName);

    List<Prices> findByPriceDate(String priceDate);

    List<Prices> findByStateName(String stateName);

//    @Query(value = "select city_name, price_date from prices where city_name = ?1 and price_date= ?2", nativeQuery = true)
//    Prices findByCityNameAndPriceDate(String cityName, String priceDate);

    @Query(value = "select * from prices where city_name = ?1 and price_date<= ?2 limit 10;", nativeQuery = true)
    List<Prices> findByPriceDateLimit(String cityName, String priceDate);

//    @Query(value = "SELECT * FROM prices p WHERE p.city_name = :city_name and p.price_date = :price_date", nativeQuery = true)
//    Prices findByCityNameAndPriceDateNamedParamsNative(@Param("city_name") String cityName, @Param("price_date") String priceDate);

}
