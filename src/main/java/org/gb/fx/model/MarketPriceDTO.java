package org.gb.fx.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
public class MarketPriceDTO {
    private final Integer id;
    private final Instrument instrument;
    private final BigDecimal bid;
    private final BigDecimal ask;
    private final ZonedDateTime timestamp;
}
