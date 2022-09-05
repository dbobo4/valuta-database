package hu.dbobo.view;

import hu.dbobo.controller.ExchangeService;
import hu.dbobo.model.valuta.ValutaId;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class AppFrame extends JFrame {

    private final ExchangeService exchangeService;

    public AppFrame(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
        initAppearance();

        JLabel fromLabel = new JLabel("from");
        ValutaComboBox fromPick = new ValutaComboBox();

        JLabel toLabel = new JLabel("from");
        ValutaComboBox toPick = new ValutaComboBox();

        JLabel amountLabel = new JLabel("amount");
        JTextField amountField = new JTextField(20);

        JButton okBtn = new JButton("OK");
        okBtn.addActionListener(e -> {
            ValutaId from = fromPick.getSelection();
            ValutaId to = toPick.getSelection();
            BigDecimal amount;
            try {
                amount = new BigDecimal(amountField.getText());
            } catch (NumberFormatException nfe) {
                amount = BigDecimal.ZERO;
                // TODO: show Error Dialog
            }
            exchangeService.exchange(from, to, amount);
        });

        this.add(fromLabel);
        this.add(fromPick);
        this.add(toLabel);
        this.add(toPick);
        this.add(amountLabel);
        this.add(amountField);
        this.add(okBtn);


        this.setVisible(true);
    }

    private void initAppearance() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(600, 400);
        this.setLayout(new GridLayout(4, 2));
        this.setLocationRelativeTo(null);

        this.setResizable(false);
    }

    private static class ValutaComboBox extends JComboBox<ValutaId> {

        private ValutaComboBox() {
            super(ValutaId.values());
        }

        private ValutaId getSelection() {
            return (ValutaId) getSelectedItem();
        }
    }


}
