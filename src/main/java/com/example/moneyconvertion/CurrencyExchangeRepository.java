package com.example.moneyconvertion;

import org.springframework.data.jpa.repository.JpaRepository;

interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long> {
}
