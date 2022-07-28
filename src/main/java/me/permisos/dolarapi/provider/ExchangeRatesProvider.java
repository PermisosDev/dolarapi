package me.permisos.dolarapi.provider;

import io.activej.http.RoutingServlet;
import me.permisos.dolarapi.Utils;
import me.permisos.dolarapi.models.MainObject;
import me.permisos.dolarapi.models.ExchangeRate;
import me.permisos.dolarapi.models.Price;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ExchangeRatesProvider {
    private static List<Price> ratesJSON = new ArrayList<>();

    private static List<Price> retrieveExchangeRates() {
        String response = Utils.get("https://www.dolarsi.com/api/api.php?type=dolar");
        return Utils.parseResponse(response);
    }

    /**
     * TODO: get specific currency from cache
     **/
    public static ExchangeRate getRate(String exchangeRate) {
        Optional<Price> rate = ratesJSON.stream().filter(c -> c.exchange_rate.nombre.equals(exchangeRate)).findFirst();
        if(rate.isEmpty()) throw new IllegalStateException("Price is Empty");
        double buy = Double.parseDouble(rate.get().exchange_rate.compra.replace(",", "."));
        double sell = Double.parseDouble(rate.get().exchange_rate.venta.replace(",", "."));
        return new ExchangeRate(buy, sell, exchangeRate);
    }

    /**
     * should only be called by {@link Utils#makeJSON()}
     * @return {@link ArrayList} with all the rates
     * @see ExchangeRate
     **/
    public static ArrayList<ExchangeRate> getAllRates() {
        ArrayList<ExchangeRate> rates = new ArrayList<>(ratesJSON.size());
        ratesJSON.forEach(data -> {
            double buy = Double.parseDouble(data.exchange_rate.compra.replace(",", "."));
            double sell = Double.parseDouble(data.exchange_rate.venta.replace(",", "."));
            String name = data.exchange_rate.nombre;
            rates.add(new ExchangeRate(buy, sell, name));
        });
        Utils.getCache().put("allRates", rates);
        return (ArrayList<ExchangeRate>) Utils.getCache().getIfPresent("allRates");
    }

    /**
     * @return {@link String} with all the rates
     * @see ExchangeRate
     **/
    public static String getAllRatesJSON() {
        if(!ratesJSON.isEmpty()) ratesJSON.clear();
        ratesJSON = retrieveExchangeRates();
        Utils.makeJSON();
        return (String) Utils.getCache().getIfPresent("rates");
    }
}
