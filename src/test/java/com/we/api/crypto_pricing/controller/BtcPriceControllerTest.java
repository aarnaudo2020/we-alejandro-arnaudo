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
    void whenGetPriceByTimeValid_thenFoundOk() {
        Price price1 = Price.builder().priceValue(57992.7).curr1("BTC").curr2("USD").build();
        String timestamp = DateTimeUtils.nowDateTimeAsString();
        Price p = btcPricingService.setNewPrice(timestamp, price1);
        assertEquals(timestamp, p.getTimestamp());
        assertEquals(57992.7,p.getPriceValue());
    }

    @Test
    void whenGetPriceByTimeNoValid_ThenReturnNotFound() {
        Price price1 = Price.builder().priceValue(61300.43).curr1("BTC").curr2("USD").build();
        String timestamp = "2021-04-06T18:10:10";
        btcPricingService.setNewPrice(DateTimeUtils.nowDateTimeAsString(), price1);
        assertEquals(404,btcPriceController.getPriceByTime(timestamp).getStatusCode().value());
    }
}
