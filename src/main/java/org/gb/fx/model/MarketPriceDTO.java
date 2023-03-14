package org.gb.fx.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonPropertyOrder({"id", "instrument", "bid", "ask", "timestamp"})
public class MarketPriceDTO {
    private Integer id;
    private Instrument instrument;
    private BigDecimal bid;
    private BigDecimal ask;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss:SSS", timezone = "UTC")
    private ZonedDateTime timestamp;
}
