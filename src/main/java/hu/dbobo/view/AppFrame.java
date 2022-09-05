package hu.dbobo.view;

import hu.dbobo.controller.ExchangeService;

import javax.swing.*;

public class AppFrame extends JFrame {

    private final ExchangeService exchangeService;
    public AppFrame(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }
}
