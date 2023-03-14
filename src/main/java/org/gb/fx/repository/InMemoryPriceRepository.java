package org.gb.fx.repository;

import org.gb.fx.model.Instrument;
import org.gb.fx.model.MarketPrice;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryPriceRepository implements PriceRepository {

    private final Map<Instrument, MarketPrice> store;

    public InMemoryPriceRepository() {
        this.store = Collections.synchronizedMap(new EnumMap<>(Instrument.class));
    }

    @Override
    public Optional<MarketPrice> getLatestPrice(Instrument instrument) {
        return Optional.ofNullable(store.get(instrument));
    }

    @Override
    public void storeLatestPrice(MarketPrice marketPrice) {
        store.put(marketPrice.getInstrument(), marketPrice);
    }
}
