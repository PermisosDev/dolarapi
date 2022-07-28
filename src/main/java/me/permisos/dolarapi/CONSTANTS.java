package me.permisos.dolarapi;

import me.permisos.dolarapi.provider.ExchangeRatesProvider;

import java.util.stream.Collectors;

public final class CONSTANTS {
    final static String ratesFinal = ExchangeRatesProvider.getAllRates().stream()
            .map(
                    e ->
                            "\""
                                    + e.getName()
                                    + "\""
                                    + ":"
                                    + "{"
                                    + "\"compra\": "
                                    + "\""
                                    + e.getBuyPrice()
                                    + "\""
                                    + ","
                                    + "\"venta\":"
                                    + "\""
                                    + e.getSellPrice()
                                    + "\""
                                    + "}")
            .collect(Collectors.joining(","));
}
