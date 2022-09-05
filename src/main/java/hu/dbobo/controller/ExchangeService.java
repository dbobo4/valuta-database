package hu.dbobo.controller;

import hu.dbobo.model.valuta.ValutaId;

import java.math.BigDecimal;
import java.util.Optional;

public interface ExchangeService {

    Optional<BigDecimal> exchange(ValutaId from, ValutaId to, BigDecimal amount);

    Optional<BigDecimal> checkRate(ValutaId from, ValutaId to, BigDecimal amount);


}
