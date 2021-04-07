package com.we.api.crypto_pricing.controller;

import com.we.api.crypto_pricing.entity.Price;
import com.we.api.crypto_pricing.service.BtcPricingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/we/crypto/")
public class BtcPriceController {

    private final BtcPricingService btcPricingService;

    public BtcPriceController(BtcPricingService btcPricingService) {
        this.btcPricingService = btcPricingService;
    }

    @GetMapping(value="/btc/usd/head")
    public ResponseEntity<List<Price>> getLastTenPrices(){
        return ResponseEntity.ok(btcPricingService.getLastTenPrices());
    }

    /**
     * only for debugging purposes
     * @return
     */
    @GetMapping(value="/btc/usd/all")
    public ResponseEntity<Map<String, Price>> getAllPrices(){
        return ResponseEntity.ok(btcPricingService.getAllPrices());
    }

    @GetMapping(value="/btc/usd/{timestamp}")
    public ResponseEntity<Optional<Price>> getPriceOnTime(@PathVariable String timestamp){
        Optional<Price> price = btcPricingService.getPriceByTimeStamp(timestamp);
        if(price.isPresent()){
            return ResponseEntity.ok(price);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value= "btc/usd/statistics")
    public ResponseEntity<BtcPriceStatisticsDto> getStatistics(@RequestParam ("from") String from,@RequestParam ("to") String to){
        if(from.isEmpty() || to.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(btcPricingService.getStatisticsDto().get());
    }

    @DeleteMapping(value="/btc")
    public ResponseEntity flushAllPrices(){
        btcPricingService.flushPrices();
        return ResponseEntity.ok("Ok");
    }
}
