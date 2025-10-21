package com.example.moneyconvertion;


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

        CurrencyExchange exchange = new CurrencyExchange(from, to, amount, Instant.now());
        return repository.saveAndFlush(exchange);
    }

    @Transactional(readOnly = true)
    public List<CurrencyExchange> listAll() {
        return repository.findAll();
    }
}

