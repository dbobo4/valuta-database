package hu.dbobo.controller;

import hu.dbobo.model.changeevent.ChangeEvent;
import hu.dbobo.model.changeevent.ChangeEventManager;
import hu.dbobo.model.valuta.ValutaCache;
import hu.dbobo.model.valuta.ValutaId;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExchangeServiceImpl implements ExchangeService {

    private ValutaCache valutaCache;
    private ChangeEventManager changeEventManager;

    public ExchangeServiceImpl(ValutaCache valutaCache, ChangeEventManager changeEventManager) {
        this.valutaCache = valutaCache;
        this.changeEventManager = changeEventManager;
    }

    @Override
    public BigDecimal exchange(ValutaId from, ValutaId to, BigDecimal amount) {
        double currentRate = valutaCache.getExchangeRate(from, to);
        BigDecimal amountBigDecimal = new BigDecimal(String.valueOf(amount));
        ChangeEvent changeEvent = new ChangeEvent(from, to, amount.doubleValue(), LocalDate.now());
        changeEventManager.insertEvent(changeEvent);
        return amountBigDecimal.multiply(BigDecimal.valueOf(currentRate));
    }

    @Override
    public BigDecimal checkRate(ValutaId from, ValutaId to, BigDecimal amount) {
        double currentRate = valutaCache.getExchangeRate(from, to);
        BigDecimal amountBigDecimal = new BigDecimal(String.valueOf(amount));
        return amountBigDecimal.multiply(BigDecimal.valueOf(currentRate));
    }

    @Override
    public boolean setExchangeRate(ValutaId from, ValutaId to, double rate) {
        return false;
    }


}
