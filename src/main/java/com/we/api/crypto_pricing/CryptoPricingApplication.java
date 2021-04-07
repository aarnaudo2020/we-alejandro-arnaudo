package com.we.api.crypto_pricing;

import com.we.api.crypto_pricing.client.CryptoPriceClient;
import com.we.api.crypto_pricing.service.BtcPricingService;
import com.we.api.crypto_pricing.utils.SchedulingTask;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CryptoPricingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CryptoPricingApplication.class, args);
	}

	@Bean
	public SchedulingTask task() {
		return new SchedulingTask(new BtcPricingService(new CryptoPriceClient()));
	}

}
