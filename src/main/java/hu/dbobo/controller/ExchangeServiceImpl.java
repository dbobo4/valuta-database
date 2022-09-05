package hu.dbobo.controller;

import hu.dbobo.model.changeevent.ChangeEvent;
import hu.dbobo.model.changeevent.ChangeEventManager;
import hu.dbobo.model.valuta.ValutaCache;
import hu.dbobo.model.valuta.ValutaId;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class ExchangeServiceImpl implements ExchangeService {

    private ValutaCache valutaCache;
    private ChangeEventManager changeEventManager;

    public ExchangeServiceImpl(ValutaCache valutaCache, ChangeEventManager changeEventManager) {
        this.valutaCache = valutaCache;
        this.changeEventManager = changeEventManager;
    }


    @Override
    public Optional<BigDecimal> exchange(ValutaId from, ValutaId to, BigDecimal amount) {
        if (from == null || to == null || amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return Optional.empty();
        }
        double currentRate = valutaCache.getExchangeRate(from, to);
        ChangeEvent changeEvent = new ChangeEvent(from, to, amount.doubleValue(), LocalDate.now());
        changeEventManager.insertEvent(changeEvent);
        return Optional.of(amount.multiply(BigDecimal.valueOf(currentRate)));
    }

    @Override
    public Optional<BigDecimal> checkRate(ValutaId from, ValutaId to, BigDecimal amount) {
        if (from == null || to == null || amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return Optional.empty();
        }
        double currentRate = valutaCache.getExchangeRate(from, to);
        return Optional.of(amount.multiply(BigDecimal.valueOf(currentRate)));
    }

    @Override
    public boolean setExchangeRate(ValutaId from, ValutaId to, double rate) {
        return false;
    }


}
