package org.gb.fx.repository;

import org.gb.fx.model.Instrument;
import org.gb.fx.model.MarketPrice;

import java.util.Optional;

public interface PriceRepository {

    Optional<MarketPrice> getLatestPrice(final Instrument instrument);

    void storeLatestPrice(final MarketPrice marketPrice);
}
