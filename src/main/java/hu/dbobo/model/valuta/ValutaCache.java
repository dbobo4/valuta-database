package hu.dbobo.model.valuta;

import hu.dbobo.exceptions.IncompleteDatabaseException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ValutaCache {
    private static ValutaCache INSTANCE;
    private final Set<Valuta> valutas;

    public static ValutaCache getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ValutaCache();
        }
        return INSTANCE;
    }

    private ValutaCache() {
        this.valutas = new HashSet<>();
        valutas.addAll(new ValutaManager().findAll());
        if (!isEveryValutaPresent()) {
            throw new IncompleteDatabaseException("The database is missing one or more currencies!");
        }
    }

    private boolean isEveryValutaPresent() {
        return Arrays.stream(ValutaId.values())
                .filter(v -> !doesValutaExist(v))
                .toList()
                .isEmpty();
    }

    private boolean doesValutaExist(ValutaId valutaId) {
        return valutas.stream()
                .anyMatch(valuta -> valuta.getValutaId() == valutaId);
    }

    public void setExchangeRate(ValutaId from, ValutaId to, double rate) {
        this.get(from).setCurrencyValue(to, rate);
        this.get(to).setCurrencyValue(from, 1 / rate);
    }

    private Valuta get(ValutaId valutaId) {
        return valutas.stream()
                .filter(valuta -> valuta.getValutaId() == valutaId)
                .findFirst()
                .orElseThrow();
    }

    public double getExchangeRate(ValutaId from, ValutaId to) {
        return this.get(from).getCurrencyValue(to);
    }

}
