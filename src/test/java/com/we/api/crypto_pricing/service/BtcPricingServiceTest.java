package com.we.api.crypto_pricing.service;

import com.we.api.crypto_pricing.client.CryptoPriceClient;
import com.we.api.crypto_pricing.entity.Price;
import com.we.api.crypto_pricing.utils.DateTimeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BtcPricingServiceTest {
    CryptoPriceClient cryptoPriceClient = Mockito.mock(CryptoPriceClient.class);
    BtcPricingService btcPricingService = new BtcPricingService(cryptoPriceClient);

    @Test
    void setNewPrice_Ok() {
        String timestamp = DateTimeUtils.nowDateTimeAsString();
        Price p = Price.builder().priceValue(60000.10).curr1("BTC").curr2("USD").timestamp(timestamp).build();
        btcPricingService.setNewPrice(timestamp,p);
        assertEquals(btcPricingService.getLastPrice(),p);
    }

    @Test
    void pullNewPrice_Ok() {
        Mockito.when(cryptoPriceClient.getBtcPrice("USD")).thenReturn(Mono.just("{\"lprice\":\"57695.7\",\"curr1\":\"BTC\",\"curr2\":\"USD\"}"));
        String timestamp = DateTimeUtils.nowDateTimeAsString();
        btcPricingService.pullNewPrice();
        assertEquals(btcPricingService.getLastPrice().getPriceValue(),57695.7);
        assertEquals(btcPricingService.getLastPrice().getCurr1(),"BTC");
        assertEquals(btcPricingService.getLastPrice().getCurr2(),"USD");
    }
}
