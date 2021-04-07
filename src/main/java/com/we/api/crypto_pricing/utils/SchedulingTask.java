package com.we.api.crypto_pricing.utils;

import com.we.api.crypto_pricing.service.BtcPricingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class SchedulingTask {

    private static final long DELAY = (long) 10 * 1000;
    private final Logger log = LoggerFactory.getLogger(SchedulingTask.class);
    private final BtcPricingService btcPricingService;

    public SchedulingTask(BtcPricingService btcPricingService) {
        this.btcPricingService = btcPricingService;
    }

    @Scheduled(fixedRate = DELAY)
    public void monitoringPrice() {
        log.info("Pulling new price ");
        btcPricingService.pullNewPrice();
    }
}
