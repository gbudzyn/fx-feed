package org.gb.fx.calculator;

import org.gb.fx.model.Instrument;
import org.gb.fx.model.MarketPrice;
import org.gb.fx.model.MarketPriceDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MarginCalculatorTest {

    private static final int TEST_ID = 123;
    private static final BigDecimal TEST_ASK = new BigDecimal("123.33");
    private static final BigDecimal TEST_BID = new BigDecimal("123.7651");

    private final Calculator<MarketPriceDTO, MarketPrice> calculator = new MarginCalculator();

    @Test
    public void should_correctly_calculate_margin() {
        // GIVEN
        final MarketPriceDTO marketPriceDTO = new MarketPriceDTO(TEST_ID, Instrument.EUR_JPY, TEST_BID, TEST_ASK, ZonedDateTime.now());

        // WHEN
        final MarketPrice result = calculator.calculate(marketPriceDTO);

        // THEN
        assertEquals(TEST_ID, result.getId());
        assertEquals(Instrument.EUR_JPY, result.getInstrument());
        assertNotNull(result.getTimestamp());

        assertEquals(TEST_ASK, result.getOriginalAsk());
        assertEquals(MarginCalculator.DEFAULT_ASK_MARGIN, result.getAskMargin());
        assertEquals(new BigDecimal("0.12333"), result.getAskMarginAmount());
        assertEquals(new BigDecimal("123.45333"), result.getAsk());
        assertTrue(result.getAsk().compareTo(TEST_ASK) > 0);

        assertEquals(TEST_BID, result.getOriginalBid());
        assertEquals(MarginCalculator.DEFAULT_BID_MARGIN, result.getBidMargin());
        assertEquals(new BigDecimal("0.1237651"), result.getBidMarginAmount());
        assertEquals(new BigDecimal("123.6413349"), result.getBid());
        assertTrue(result.getBid().compareTo(TEST_BID) < 0);
    }
}