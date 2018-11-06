package de.banksapi.client.services;

import de.banksapi.client.BANKSapi;
import de.banksapi.client.SimpleBANKSapi;
import de.banksapi.client.services.internal.DefaultHTTPClientFactory;
import de.banksapi.client.services.internal.IHTTPClientFactory;

import java.net.URL;

public abstract class AbstractBaseService {

    private BANKSapi bankSapi = new SimpleBANKSapi();

    private IHTTPClientFactory clientFactory = new DefaultHTTPClientFactory();

    public void setClientFactory(IHTTPClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    protected IHTTPClientFactory getClientFactory() {
        return clientFactory;
    }

    protected URL getBanksApiBase() {
        return bankSapi.getBanksapiBaseUrl();
    }

    public void setBankSapi(BANKSapi bankSapi) {
        this.bankSapi = bankSapi;
    }

}
