package com.we.api.crypto_pricing.client;

import io.netty.channel.ChannelOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;


@Component
public class CryptoPriceClient {
    private Logger log = LoggerFactory.getLogger(CryptoPriceClient.class);

    private static final String URL_BASE="https://cex.io/api/last_price";

    private final WebClient webClient;

    public CryptoPriceClient() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);
        this.webClient = WebClient.builder()
                .baseUrl(URL_BASE)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeader(HttpHeaders.ACCEPT, "text/json")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "text/json")
                .build();
    }

    public Mono<String> getBtcPrice(String currencyCode2) {
        return this.webClient
                .get()
                .uri("/BTC/" + currencyCode2)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> {
                    log.error(String.format("Error http %s getting price",response.statusCode()));
                    return Mono.error(new RuntimeException(String.format("Error %s on pulling price",response.statusCode())));
                })
                .onStatus(HttpStatus::is5xxServerError, response -> {
                    log.error(String.format("Error http %s getting price %s",response.statusCode()));
                    return Mono.error(new RuntimeException(String.format("Error %s pulling price ", response.statusCode())));
                })
                .bodyToMono(String.class);
                //.log();
    }


}
