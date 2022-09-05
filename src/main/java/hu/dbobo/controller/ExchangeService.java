package hu.dbobo.controller;

import hu.dbobo.model.valuta.ValutaId;

import java.math.BigDecimal;

public interface ExchangeService {

    BigDecimal exchange(ValutaId from, ValutaId to, BigDecimal amount);

    BigDecimal checkRate(ValutaId from, ValutaId to, BigDecimal amount);

    boolean setExchangeRate(ValutaId from, ValutaId to, double rate);

}
