package org.gb.fx.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Instrument {
    @JsonProperty("EUR/USD")
    EUR_USD,
    @JsonProperty("EUR/JPY")
    EUR_JPY,
    @JsonProperty("GBP/USD")
    GBP_USD,
}
