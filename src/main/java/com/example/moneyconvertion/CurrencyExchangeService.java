package com.example.moneyconvertion;

import org.javamoney.moneta.Money;
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.convert.ConversionQueryBuilder;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class CurrencyExchangeService {

    private final CurrencyExchangeRepository repository;

    CurrencyExchangeService(CurrencyExchangeRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public CurrencyExchange convertAndSave(String from, String to, double amount) {
        CurrencyUnit fromUnit = Monetary.getCurrency(from);
        CurrencyUnit toUnit = Monetary.getCurrency(to);
        MonetaryAmount fromAmount = Money.of(amount, fromUnit);
        CurrencyConversion conversion = MonetaryConversions.getConversion(
                ConversionQueryBuilder.of().setTermCurrency(toUnit).build()
        );
        MonetaryAmount toAmount = fromAmount.with(conversion);
        double result = toAmount.getNumber().doubleValue();

        CurrencyExchange exchange = new CurrencyExchange(from, to, amount, result, Instant.now());
        return repository.saveAndFlush(exchange);
    }

    @Transactional(readOnly = true)
    public List<CurrencyExchange> listAll() {
        return repository.findAll();
    }
}

