package org.gb.fx.calculator;

import org.gb.fx.model.MarketPrice;
import org.gb.fx.model.MarketPriceDTO;

import java.math.BigDecimal;

public class MarginCalculator implements Calculator<MarketPriceDTO, MarketPrice> {
    protected static final BigDecimal DEFAULT_ASK_MARGIN = new BigDecimal("0.001");
    protected static final BigDecimal DEFAULT_BID_MARGIN = new BigDecimal("0.001");

    @Override
    public MarketPrice calculate(final MarketPriceDTO marketPriceDTO) {
        final BigDecimal askMarginAmount = marketPriceDTO.getAsk().multiply(DEFAULT_ASK_MARGIN);
        final BigDecimal bidMarginAmount = marketPriceDTO.getBid().multiply(DEFAULT_BID_MARGIN);

        return MarketPrice.builder()
                .id(marketPriceDTO.getId())
                .instrument(marketPriceDTO.getInstrument())
                .originalAsk(marketPriceDTO.getAsk())
                .askMargin(DEFAULT_ASK_MARGIN)
                .askMarginAmount(askMarginAmount)
                .ask(marketPriceDTO.getAsk().add(askMarginAmount))
                .originalBid(marketPriceDTO.getBid())
                .bidMargin(DEFAULT_BID_MARGIN)
                .bidMarginAmount(bidMarginAmount)
                .bid(marketPriceDTO.getBid().subtract(bidMarginAmount))
                .timestamp(marketPriceDTO.getTimestamp())
                .build();
    }
}
