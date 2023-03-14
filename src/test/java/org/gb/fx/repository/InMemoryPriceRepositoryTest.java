package org.gb.fx.repository;

import org.gb.fx.model.Instrument;
import org.gb.fx.model.MarketPrice;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryPriceRepositoryTest {

    private final PriceRepository repository = new InMemoryPriceRepository();

    @Test
    public void should_store_and_return_stored_value() {
        // GIVEN
        final MarketPrice price = MarketPrice.builder()
                .id(1)
                .ask(new BigDecimal("1"))
                .bid(new BigDecimal("2"))
                .instrument(Instrument.EUR_JPY)
                .build();

        // WHEN
        repository.storeLatestPrice(price);

        final Optional<MarketPrice> retrievedPrice = repository.getLatestPrice(price.getInstrument());

        // THEN
        assertTrue(retrievedPrice.isPresent());
        assertEquals(price, retrievedPrice.get());
    }

    @Test
    public void should_store_only_latest_price_for_instrument() {
        // GIVEN
        final MarketPrice price = MarketPrice.builder()
                .id(1)
                .ask(new BigDecimal("1"))
                .bid(new BigDecimal("2"))
                .instrument(Instrument.EUR_JPY)
                .build();
        final MarketPrice newerPrice = MarketPrice.builder()
                .id(1)
                .ask(new BigDecimal("2"))
                .bid(new BigDecimal("3"))
                .instrument(Instrument.EUR_JPY)
                .build();

        // WHEN
        repository.storeLatestPrice(price);
        repository.storeLatestPrice(newerPrice);

        final Optional<MarketPrice> retrievedPrice = repository.getLatestPrice(price.getInstrument());

        // THEN
        assertTrue(retrievedPrice.isPresent());
        assertEquals(newerPrice, retrievedPrice.get());
    }

    @Test
    public void should_return_empty_optional_if_value_not_present() {
        // GIVEN
        // WHEN
        final Optional<MarketPrice> retrievedPrice = repository.getLatestPrice(Instrument.EUR_USD);

        // THEN
        assertTrue(retrievedPrice.isEmpty());
    }
}