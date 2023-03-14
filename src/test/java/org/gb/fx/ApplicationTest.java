package org.gb.fx;

import lombok.extern.slf4j.Slf4j;
import org.gb.fx.calculator.Calculator;
import org.gb.fx.calculator.MarginCalculator;
import org.gb.fx.model.Instrument;
import org.gb.fx.model.MarketPrice;
import org.gb.fx.model.MarketPriceDTO;
import org.gb.fx.processor.PriceProcessor;
import org.gb.fx.repository.InMemoryPriceRepository;
import org.gb.fx.repository.PriceRepository;
import org.junit.jupiter.api.Test;

@Slf4j
class ApplicationTest {

    private Calculator<MarketPriceDTO, MarketPrice> marginCalculator = new MarginCalculator();

    private PriceRepository repository = new InMemoryPriceRepository();

    private PriceProcessor priceProcessor = new PriceProcessor(marginCalculator, repository);

    @Test
    public void should_process_multiple_messages_and_store_prices() {
        // GIVEN
        final String[] lines = new String[]{
                "106,EUR/USD,1.1000,1.2000,01-06-2020 12:01:01:001",
                "107,EUR/JPY,119.60,119.90,01-06-2020 12:01:02:002",
                "108,GBP/USD,1.2499,1.2561,01-06-2020 12:01:02:002",
                "109,GBP/USD,1.2499,1.2561,01-06-2020 12:01:02:100",
                "110,EUR/JPY,119.61,119.91,01-06-2020 12:01:02:110",
        };

        // WHEN
        for (final String line : lines) {
            priceProcessor.onMessage(line);
        }

        // THEN
        log.info("Price for EUR/USD: {}", repository.getLatestPrice(Instrument.EUR_USD).get());
        log.info("Price for EUR/JPY: {}", repository.getLatestPrice(Instrument.EUR_JPY).get());
        log.info("Price for GBP/USD: {}", repository.getLatestPrice(Instrument.GBP_USD).get());
    }
}