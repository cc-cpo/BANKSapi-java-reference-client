package de.banksapi.client.services;

import de.banksapi.client.BanksapiConfig;
import de.banksapi.client.services.internal.Preconditions;

import java.net.URL;

public abstract class AbstractBaseService {

    private BanksapiConfig banksapiConfig;

    private IHTTPClientFactory clientFactory;

    private ICorrelationIdStrategy correlationIdStrategy;

    private IRequestFilter requestFilter;

    public void setClientFactory(IHTTPClientFactory clientFactory) {
        Preconditions.checkState(this.clientFactory == null, "Already defined!");
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
        Preconditions.checkState(this.banksapiConfig == null, "Already defined!");
        this.banksapiConfig = bankSapi;
    }

    /**
     * @return optional strategy
     */
    public ICorrelationIdStrategy getCorrelationIdStrategy() {
        return correlationIdStrategy;
    }

    public void setCorrelationIdStrategy(ICorrelationIdStrategy correlationIdStrategy) {
        Preconditions.checkState(this.correlationIdStrategy == null, "Already defined!");
        this.correlationIdStrategy = correlationIdStrategy;
    }

    /**
     * @return optional filter
     */
    public IRequestFilter getRequestFilter() {
        return requestFilter;
    }

    public void setRequestFilter(IRequestFilter requestFilter) {
        Preconditions.checkState(this.requestFilter == null, "Already defined!");
        this.requestFilter = requestFilter;
    }
}
