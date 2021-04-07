package com.we.api.crypto_pricing.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties
public class Price {

    String timestamp;
    @JsonProperty("lprice")
    private double priceValue;
    @JsonProperty("curr1")
    private String curr1;
    @JsonProperty("curr2")
    private String curr2;

    @JsonValue()
    public Map<String,Object> getPrice() {
        Map price = new HashMap();
        price.put("timestamp",timestamp);
        price.put("price",this.priceValue);
        price.put("currency",this.curr2);
        return price;
    }

    @Override
    public String toString() {
        return "Price{" +
                "price  =" + priceValue +
                ", curr1 ='" + curr1 + '\'' +
                ", curr2 ='" + curr2 + '\'' +
                '}';
    }
}
