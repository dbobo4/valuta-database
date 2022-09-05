package hu.dbobo.model.changeevent;

import hu.dbobo.model.valuta.ValutaId;

import java.time.LocalDate;
import java.util.Objects;

public class ChangeEvent {
    private final Integer change_id;
    private final ValutaId valuta_from;
    private final ValutaId valuta_to;
    private final double amount;
    private final LocalDate change_date;

    public ChangeEvent(int change_id, ValutaId valuta_from, ValutaId valuta_to, double amount, LocalDate change_date) {
        this.change_id = change_id;
        this.valuta_from = Objects.requireNonNull(valuta_from);
        this.valuta_to = Objects.requireNonNull(valuta_to);
        this.amount = amount;
        this.change_date = Objects.requireNonNull(change_date);
    }

    public ChangeEvent(ValutaId valuta_from, ValutaId valuta_to, double amount, LocalDate change_date) {
        this.change_id = null;
        this.valuta_from = valuta_from;
        this.valuta_to = valuta_to;
        this.amount = amount;
        this.change_date = change_date;
    }

    public Integer getChange_id() {
        return change_id;
    }

    public ValutaId getValuta_from() {
        return valuta_from;
    }

    public ValutaId getValuta_to() {
        return valuta_to;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getChange_date() {
        return change_date;
    }
}
