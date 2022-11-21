package com.example.MetricTaxi.repository;

import com.example.MetricTaxi.model.MomentPrice;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PriceRepository extends CrudRepository<MomentPrice,Long> {
    List<MomentPrice> findAll();
}
