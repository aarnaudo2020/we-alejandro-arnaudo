package com.we.api.crypto_pricing.controller;

import com.we.api.crypto_pricing.entity.Price;
import com.we.api.crypto_pricing.entity.dto.BtcPriceStatisticsDto;
import com.we.api.crypto_pricing.service.BtcPricingService;
import com.we.api.crypto_pricing.utils.DateTimeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<Optional<Price>> getPriceByTime(@PathVariable String timestamp){
        if(!DateTimeUtils.isValid(timestamp)){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid date");
        }
        Optional<Price> price = btcPricingService.getPriceByTimeStamp(timestamp);
        if(price.isPresent()){
            return ResponseEntity.ok(price);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value= "btc/usd/statistics")
    public ResponseEntity<BtcPriceStatisticsDto> getStatistics(@RequestParam ("from") String from, @RequestParam ("to") String to){
        if(from.isEmpty() || to.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "From or To parameter are not present");
        }
        if(!DateTimeUtils.isValid(from)){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid date from");
        }
        if(!DateTimeUtils.isValid(to)){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid date to");
        }
        if(from.compareTo(to) > 0){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "From must be less than to");
        }

        Optional<BtcPriceStatisticsDto> statsDto = btcPricingService.getStatisticsDto(from,to);

        return statsDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(value="/btc")
    public ResponseEntity<String> flushAllPrices(){
        btcPricingService.flushPrices();
        return ResponseEntity.ok("Ok");
    }
}
