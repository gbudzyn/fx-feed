# fx-feed

Full test that can be used to test application - org.gb.fx.ApplicationTest

### Assumptions
- Incoming data format and type compatibility with DTO model (no other Instrument enum values, prices parse-able to BidDecimal, Data in format from documentation)
- Line separator in message is '\n'
- No data consistency check (can be easily added) - no checking for incoming price range errors (Ask lower than Buy etc.)
- Internally stored timestamp is same as incoming one