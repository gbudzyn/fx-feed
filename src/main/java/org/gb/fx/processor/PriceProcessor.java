package org.gb.fx.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.gb.fx.calculator.Calculator;
import org.gb.fx.model.MarketPrice;
import org.gb.fx.model.MarketPriceDTO;
import org.gb.fx.repository.PriceRepository;

@Slf4j
public class PriceProcessor implements Processor {

    private final Calculator<MarketPriceDTO, MarketPrice> marginCalculator;
    private final PriceRepository repository;

    final CsvMapper mapper;

    public PriceProcessor(final Calculator<MarketPriceDTO, MarketPrice> marginCalculator, PriceRepository repository) {
        this.mapper = (CsvMapper) new CsvMapper().registerModule(new JavaTimeModule());

        this.marginCalculator = marginCalculator;
        this.repository = repository;
    }

    @Override
    public void onMessage(final String message) {
        log.debug("Processing message {}", message);
        final String[] lines = message.split("\n");
        for (final String line : lines) {
            final MarketPriceDTO marketPrice;
            try {
                marketPrice = mapper.readerWithSchemaFor(MarketPriceDTO.class).readValue(line);
            } catch (JsonProcessingException exception) {
                log.error("Skipping line {} due to parsing exception", line, exception);

                continue;
            }

            final MarketPrice calculatedMarketPrice = marginCalculator.calculate(marketPrice);

            repository.storeLatestPrice(calculatedMarketPrice);
        }
    }
}
