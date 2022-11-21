package com.example.MetricTaxi.service;

import com.example.MetricTaxi.apiclient.TaxiApiClient;
import com.example.MetricTaxi.model.Coordinate;
import com.example.MetricTaxi.model.MomentPrice;
import com.example.MetricTaxi.model.Price;
import com.example.MetricTaxi.properties.YandexProperties;
import com.example.MetricTaxi.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaxiService {
    private final YandexProperties yandexProperties;
    private final TaxiApiClient taxiApiClient;
    private final PriceRepository priceRepository;

    public void getPrice(Coordinate startPoint, Coordinate endPoint) {
        String rll = startPoint.toString() + "~" + endPoint.toString();
        String clid = yandexProperties.getClid();
        String apiKey = yandexProperties.getApiKey();

        Price currentPrice  = taxiApiClient.getPrice(clid,apiKey,rll);
        if (currentPrice.getOptions().isEmpty()) {
            throw new RuntimeException("Options are empty");
        }
        double priceDouble = currentPrice.getOptions().get(0).getPrice();
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
