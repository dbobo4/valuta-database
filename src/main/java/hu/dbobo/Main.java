package hu.dbobo;

import hu.dbobo.config.DerbyConfig;
import hu.dbobo.model.valuta.ValutaCache;

public class Main {
    public static void main(String[] args) {
        // initialising database
        DerbyConfig.initDatabase();

        ValutaCache valutaCache = ValutaCache.getInstance();
        // TODO: Instantiate a ChangeEventManager/ChangeEventRepository.
        // TODO: Instantiate an ExchangeService implementation.

    }
}
