package com.we.api.crypto_pricing.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BtcPriceStatisticsDto {
    @JsonProperty("price_avg")
    private double priceAvg;
    @JsonProperty("variation_coefficient")
    private double variationCoefficient;
    @JsonProperty("max_price")
    private double maxPrice;
}
