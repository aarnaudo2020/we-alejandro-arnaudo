package com.we.api.crypto_pricing.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.we.api.crypto_pricing.client.CryptoPriceClient;
import com.we.api.crypto_pricing.controller.BtcPriceStatisticsDto;
import com.we.api.crypto_pricing.entity.Price;
import com.we.api.crypto_pricing.utils.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

@Service
public class BtcPricingService {

    private final ConcurrentSkipListMap<String, Price> btcPriceMap;
    private final CryptoPriceClient cryptoPriceClient;
    private final Logger log = LoggerFactory.getLogger(BtcPricingService.class);

    public BtcPricingService(CryptoPriceClient cryptoPriceClient) {
        this.btcPriceMap = new ConcurrentSkipListMap<>();
        this.cryptoPriceClient = cryptoPriceClient;
    }

    public Price setNewPrice(String timestampKey, Price price) {
        price.setTimestamp(timestampKey);
        this.btcPriceMap.put(timestampKey, price);
        return this.btcPriceMap.lastEntry().getValue();
    }

    public Price getLastPrice() {
        return this.btcPriceMap.lastEntry().getValue();
    }

    public Optional<Price> getPriceByTimeStamp(String timestamp) {
        return Optional.ofNullable(this.btcPriceMap.get(timestamp));
    }

    public Optional<BtcPriceStatisticsDto> getStatisticsDto() {
        Optional<BtcPriceStatisticsDto> btcPriceStatisticsDto;
        double maxPrice = this.btcPriceMap.entrySet()
                .stream()
                .map(e -> e.getValue())
                .sorted(Comparator.comparing(Price::getPriceValue))
                .mapToDouble(Price::getPriceValue)
                .max()
                .orElseThrow(NoSuchElementException::new);
        return Optional.of(BtcPriceStatisticsDto.builder().maxPrice(maxPrice).build());
    }

    public Map<String, Price> getAllPrices() {
        return this.btcPriceMap.descendingMap();
    }

    public List<Price> getLastTenPrices() {
        return this.getAllPrices().entrySet()
                .stream()
                .sorted((o1, o2) -> o2.getKey().compareTo(o1.getKey()))
                .limit(10)
                .map(o1 -> o1.getValue())
                .collect(Collectors.toList());
    }

    public void flushPrices() {
        this.btcPriceMap.clear();
    }

    public void pullNewPrice()  {
        cryptoPriceClient.getBtcPrice("USD").subscribe(price -> {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                this.setNewPrice(DateTimeUtils.nowDateTimeAsString(), objectMapper.readValue(price, Price.class));
            } catch (JsonProcessingException e) {
                log.error("Error processing new price ", e);
            }
        });
    }
}
