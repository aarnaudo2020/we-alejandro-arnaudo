package com.we.api.crypto_pricing.controller;

import com.we.api.crypto_pricing.client.CryptoPriceClient;
import com.we.api.crypto_pricing.entity.Price;
import com.we.api.crypto_pricing.service.BtcPricingService;
import com.we.api.crypto_pricing.utils.DateTimeUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BtcPriceControllerTest {
    CryptoPriceClient cryptoPriceClientMock = Mockito.mock(CryptoPriceClient.class);
    private final BtcPricingService btcPricingService = new BtcPricingService(cryptoPriceClientMock);

    private final BtcPriceController btcPriceController = new BtcPriceController(btcPricingService);


    @Test
    void getPriceOnTime_FoundOk() {
        Price price1 = Price.builder().priceValue(57992.7).curr1("BTC").curr2("USD").build();
        String timestamp = DateTimeUtils.nowDateTimeAsString();
        Price p = btcPricingService.setNewPrice(timestamp, price1);
        assertEquals(timestamp, p.getTimestamp());
        assertEquals("{price=57992.7, currency=USD, timestamp="+timestamp+"}", btcPriceController.getPriceOnTime(timestamp).getBody().get().getPrice().toString());
    }

    @Test
    void getPriceOnTime_NotFound() {
        Price price1 = Price.builder().priceValue(61300.43).curr1("BTC").curr2("USD").build();
        String timestamp = "2021-04-06T18:10:10";
        btcPricingService.setNewPrice(DateTimeUtils.nowDateTimeAsString(), price1);
        assertEquals(404,btcPriceController.getPriceOnTime(timestamp).getStatusCode().value());
    }
}
