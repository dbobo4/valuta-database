package hu.dbobo;

import hu.dbobo.config.DerbyConfig;
import hu.dbobo.controller.ExchangeService;
import hu.dbobo.controller.ExchangeServiceImpl;
import hu.dbobo.model.changeevent.ChangeEventManager;
import hu.dbobo.model.valuta.ValutaCache;
import hu.dbobo.view.AppFrame;

public class Main {
    public static void main(String[] args) {
        // initialising database
        DerbyConfig.initDatabase();

        ValutaCache valutaCache = ValutaCache.getInstance();
        ChangeEventManager changeEventManager = new ChangeEventManager();
        ExchangeService exchangeService = new ExchangeServiceImpl(valutaCache, changeEventManager);

        new AppFrame(exchangeService);

    }
}
