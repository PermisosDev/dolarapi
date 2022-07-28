package me.permisos.dolarapi.models;

public class ExchangeRate {
    private final double buyPrice;
    private final double sellPrice;
    private final String name;

    public ExchangeRate(double buyPrice, double sellPrice, String name) {
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.name = name;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }
    public String getName() {
        return name;
    }
}
