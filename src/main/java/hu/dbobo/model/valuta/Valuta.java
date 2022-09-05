package hu.dbobo.model.valuta;

import java.util.Objects;

public class Valuta {

    private final ValutaId valutaId;
    private final String valutaName;
    private Double chf;
    private Double eur;
    private Double usd;
    private Double huf;

    public Valuta(ValutaId valutaId, String valutaName, double chf, double eur, double usd, double huf) {
        if (valutaId == null || chf < 0 || eur < 0 || usd < 0 || huf < 0) {
            throw new IllegalArgumentException("The inputs for valuta are not valid!");
        }
        this.valutaId = valutaId;
        this.valutaName = valutaName;
        this.chf = chf;
        this.eur = eur;
        this.usd = usd;
        this.huf = huf;
        setCurrencyValue(valutaId, 1);
    }

    public ValutaId getValutaId() {
        return valutaId;
    }

    public String getValutaName() {
        return valutaName;
    }

    public double getCurrencyValue(ValutaId valutaId) {
        return switch (valutaId) {
            case CHF -> this.chf;
            case EUR -> this.eur;
            case USD -> this.usd;
            case HUF -> this.huf;
        };
    }

    public void setCurrencyValue(ValutaId valutaId, double value) {
        if (value < 0) {
            throw new IllegalArgumentException("The value for currency is not valid!");
        }
        switch (valutaId) {
            case CHF -> this.chf = value;
            case EUR -> this.eur = value;
            case USD -> this.usd = value;
            case HUF -> this.huf = value;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Valuta valuta = (Valuta) o;
        return valutaId == valuta.valutaId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(valutaId);
    }
}
