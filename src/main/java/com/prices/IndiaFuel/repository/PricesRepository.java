package com.prices.IndiaFuel.repository;

import com.prices.IndiaFuel.model.Prices;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricesRepository extends JpaRepository<Prices, Long> {
}
