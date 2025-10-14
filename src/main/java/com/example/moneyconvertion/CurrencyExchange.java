package com.example.moneyconvertion;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "currency_exchange")
public class CurrencyExchange {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "exchange_id")
    private Long id;

    @Column(name = "from_currency", nullable = false)
    private String fromCurrency;

    @Column(name = "to_currency", nullable = false)
    private String toCurrency;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "result", nullable = false)
    private double result;

    @Column(name = "exchange_date", nullable = false)
    private Instant exchangeDate;

    protected CurrencyExchange() {}

    public CurrencyExchange(String fromCurrency, String toCurrency, double amount, double result, Instant exchangeDate) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.amount = amount;
        this.result = result;
        this.exchangeDate = exchangeDate;
    }


    public String getFromCurrency() {
        return fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public double getAmount() {
        return amount;
    }

    public double getResult() {
        return result;
    }

    public Instant getExchangeDate() {
        return exchangeDate;
    }

}

