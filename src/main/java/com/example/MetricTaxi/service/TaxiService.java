package com.example.MetricTaxi.service;

import com.example.MetricTaxi.apiclient.TaxiApiClient;
import com.example.MetricTaxi.model.Coordinate;
import com.example.MetricTaxi.model.MomentPrice;
import com.example.MetricTaxi.model.Price;
import com.example.MetricTaxi.properties.YandexProperties;
import com.example.MetricTaxi.repository.PriceRepository;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TaxiService {
    private final TaxiApiClient taxiApiClient;
    private final PriceRepository priceRepository;
    private final YandexProperties yandexProperties;
    private AtomicInteger price;


    public TaxiService(YandexProperties yandexProperties, TaxiApiClient taxiApiClient, PriceRepository priceRepository, MeterRegistry meterRegistry) {
        this.yandexProperties = yandexProperties;
        this.taxiApiClient = taxiApiClient;
        this.priceRepository = priceRepository;
        price = new AtomicInteger();
        meterRegistry.gauge("priceTaxi",price);
    }

    public void getPrice(Coordinate startPoint, Coordinate endPoint) {
        String rll = startPoint.toString() + "~" + endPoint.toString();
        String clid = yandexProperties.getClid();
        String apiKey = yandexProperties.getApiKey();

        Price currentPrice  = taxiApiClient.getPrice(clid,apiKey,rll);
        if (currentPrice.getOptions().isEmpty()) {
            throw new RuntimeException("Options are empty");
        }
        double priceDouble = currentPrice.getOptions().get(0).getPrice();
        price.set((int)priceDouble);
        MomentPrice momentPrice = new MomentPrice(
                LocalDateTime.now(ZoneId.of("Europe/Moscow")),
                priceDouble
        );
        priceRepository.save(momentPrice);
    }

    public List<MomentPrice> getAllPrice() {
        return priceRepository.findAll();
    }
}
