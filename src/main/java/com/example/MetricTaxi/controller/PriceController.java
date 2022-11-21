package com.example.MetricTaxi.controller;

import com.example.MetricTaxi.model.MomentPrice;
import com.example.MetricTaxi.service.TaxiService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class PriceController {
    private final TaxiService taxiService;
    @GetMapping("/prices")
    public List<MomentPrice> getAllPrice() {
        return taxiService.getAllPrice();
    }
}
