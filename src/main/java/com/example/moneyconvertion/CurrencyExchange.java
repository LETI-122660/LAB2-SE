package com.example.moneyconvertion;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import org.joda.money.*;
import java.net.*;
import java.io.*;

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



    protected CurrencyExchange() {}// To keep Hibernate happy

    public CurrencyExchange(String fromCurrency, String toCurrency, double amount, Instant exchangeDate) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.amount = amount;
        this.exchangeDate = exchangeDate;

        CurrencyUnit fromCurrencyUnit = CurrencyUnit.of(fromCurrency);
        CurrencyUnit toCurrencyUnit = CurrencyUnit.of(toCurrency);
        Money fromMoney = Money.of(fromCurrencyUnit, amount);
        CurrencyUnit usd = CurrencyUnit.of("USD");
        if (fromCurrency.equals(toCurrency)) {
            this.result = amount;
            return;
        }
        try {
            JsonObject exchangeRates = getExchangeRates(fromCurrency);
            JsonObject conversion_rates = exchangeRates.getAsJsonObject("conversion_rates");
            double toRate = conversion_rates.get(toCurrency).getAsDouble();
            Money toMoney = fromMoney.convertedTo(toCurrencyUnit, BigDecimal.valueOf(toRate), RoundingMode.HALF_UP);
            this.result = toMoney.getAmount().doubleValue();
        } catch (IOException e) {
            e.printStackTrace();
            this.result = 0.0;
        }



    }

    private JsonObject getExchangeRates(String currency) throws IOException {
        // Setting URL
        String url_str = "https://v6.exchangerate-api.com/v6/458fc04a58b0e5feff7f1aa0/latest/" + currency;

        // Making Request
        URL url = new URL(url_str);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        // Convert to JSON
        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject jsonobj = root.getAsJsonObject();

        // Accessing object
        String req_result = jsonobj.get("result").getAsString();
        System.out.println("Request Result: " + req_result);
        return jsonobj;
    }



    public Long getId() {
        return id;
    }
    public String getFromCurrency() {
        return fromCurrency;
    }
    public double getAmount() {
        return amount;
    }

    public Object getToCurrency() {
        return toCurrency;
    }

    public double getResult() {
        return result;
    }
    public Instant getExchangeDate() {
        return exchangeDate;
    }
}

