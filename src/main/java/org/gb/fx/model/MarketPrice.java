package org.gb.fx.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Builder
@Getter
@ToString
public class MarketPrice {
    private final Integer id;
    private final Instrument instrument;
    private final BigDecimal originalBid;
    private final BigDecimal bidMargin;
    private final BigDecimal bidMarginAmount;
    private final BigDecimal bid;
    private final BigDecimal originalAsk;
    private final BigDecimal askMargin;
    private final BigDecimal askMarginAmount;
    private final BigDecimal ask;
    private final ZonedDateTime timestamp;
}
