package org.gb.fx.processor;

import org.gb.fx.calculator.Calculator;
import org.gb.fx.model.Instrument;
import org.gb.fx.model.MarketPrice;
import org.gb.fx.model.MarketPriceDTO;
import org.gb.fx.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceProcessorTest {

    @Mock
    private Calculator<MarketPriceDTO, MarketPrice> marginCalculator;

    @Mock
    private PriceRepository repository;

    @Captor
    private ArgumentCaptor<MarketPriceDTO> marketPriceCaptor;

    private PriceProcessor priceProcessor;

    @BeforeEach
    public void setUp() {
        priceProcessor = new PriceProcessor(marginCalculator, repository);
    }

    @Test
    public void should_correctly_process_single_line_message() {
        // GIVEN
        final String line = "106,EUR/USD,1.1000,1.2000,01-06-2020 12:01:01:001";

        final MarketPrice marketPrice = MarketPrice.builder()
                .id(1)
                .ask(new BigDecimal("1"))
                .bid(new BigDecimal("2"))
                .instrument(Instrument.EUR_JPY)
                .build();
        when(marginCalculator.calculate(marketPriceCaptor.capture())).thenReturn(marketPrice);

        // WHEN
        priceProcessor.onMessage(line);

        // THEN
        assertNotNull(marketPriceCaptor.getValue());
        final MarketPriceDTO parsedMarketPriceDTO = marketPriceCaptor.getValue();
        assertEquals(106, parsedMarketPriceDTO.getId());
        assertEquals(Instrument.EUR_USD, parsedMarketPriceDTO.getInstrument());
        assertEquals(new BigDecimal("1.1000"), parsedMarketPriceDTO.getBid());
        assertEquals(new BigDecimal("1.2000"), parsedMarketPriceDTO.getAsk());
        assertEquals(1591012861, parsedMarketPriceDTO.getTimestamp().toEpochSecond());

        verify(repository, times(1)).storeLatestPrice(eq(marketPrice));
    }
}