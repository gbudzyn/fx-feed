package org.gb.fx.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
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
        this.mapper = new CsvMapper();

        this.marginCalculator = marginCalculator;
        this.repository = repository;
    }

    @Override
    public void onMessage(final String message) {
        log.debug("Processing message {}", message);

        final MarketPriceDTO marketPrice;
        try {
            marketPrice = mapper.readerWithSchemaFor(MarketPriceDTO.class).readValue(message);
        } catch (JsonProcessingException exception) {
            log.error("Skipping message {} due to parsing exception", message, exception);

            throw new RuntimeException();
        }

        final MarketPrice calculatedMarketPrice = marginCalculator.calculate(marketPrice);

        repository.storeLatestPrice(calculatedMarketPrice);
    }
}
