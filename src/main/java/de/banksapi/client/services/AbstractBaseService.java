package de.banksapi.client.services;

import de.banksapi.client.BanksapiConfig;
import de.banksapi.client.services.internal.IHTTPClientFactory;
import de.banksapi.client.services.internal.Preconditions;
import de.banksapi.client.services.internal.SimpleHTTPClientFactory;

import java.net.URL;

public abstract class AbstractBaseService {

    private BanksapiConfig banksapiConfig;

    private IHTTPClientFactory clientFactory;

    public void setClientFactory(IHTTPClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    protected IHTTPClientFactory getClientFactory() {
        Preconditions.checkState(banksapiConfig != null,
                "Incomplete service setup - use setter injection via setClientFactory");
        return clientFactory;
    }

    protected URL getBanksapiBaseUrl() {
        Preconditions.checkState(banksapiConfig != null,
                "Incomplete service setup - use setter injection via setBanksapiConfig");
        return banksapiConfig.getBanksapiBaseUrl();
    }

    public void setBanksapiConfig(BanksapiConfig bankSapi) {
        this.banksapiConfig = bankSapi;
    }

}
